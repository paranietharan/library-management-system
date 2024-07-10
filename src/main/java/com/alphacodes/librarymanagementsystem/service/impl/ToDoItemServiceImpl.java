package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.ToDoItemDto;
import com.alphacodes.librarymanagementsystem.DTO.ToDoItemViewDto;
import com.alphacodes.librarymanagementsystem.Model.ToDoItem;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.repository.ToDoItemRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.ToDoItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToDoItemServiceImpl implements ToDoItemService {

    private final ToDoItemRepository toDoItemRepository;
    private final UserRepository userRepository;

    public ToDoItemServiceImpl(ToDoItemRepository toDoItemRepository, UserRepository userRepository) {
        this.toDoItemRepository = toDoItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ToDoItemDto> getAllToDoItemsByUserId(String userId) {
        // get all to do items by user id
        List<ToDoItem> toDoItems = toDoItemRepository.findByUserId(userId);

        // convert ToDoItem to ToDoItemDto
        List<ToDoItemDto> toDoItemDtos = toDoItems
                .stream()
                .map(this::convertToDoItemToToDoItemDto)
                .collect(Collectors.toList());

        return toDoItemDtos;
    }

    @Override
    public ToDoItemDto getToDoItemById(Long id) {
        // Get to do item by id
        ToDoItem toDoItem = toDoItemRepository.findById(id).orElse(null);

        // convert ToDoItem to ToDoItemDto
        ToDoItemDto toDoItemDto = convertToDoItemToToDoItemDto(toDoItem);
        return toDoItemDto;
    }

    @Override
    public ToDoItemViewDto saveToDoItem(ToDoItemViewDto toDoItemViewDto, String userId) {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setTitle(toDoItemViewDto.getTitle());
        toDoItem.setDescription(toDoItemViewDto.getDescription());

        // if target date is not null then set target date
        if (toDoItemViewDto.getTargetDate() != null) {
            toDoItem.setTargetDate(toDoItemViewDto.getTargetDate());
        }

        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUserID(userId));
        if (userOptional.isPresent()) {
            toDoItem.setUser(userOptional.get());
            // save to do item
            toDoItemRepository.save(toDoItem);
            return toDoItemViewDto;
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    @Override
    public ToDoItemViewDto updateToDoItem(Long id, ToDoItemViewDto toDoItemViewDto, String userId) {
        // Get to do item by id
        ToDoItem toDoItem = toDoItemRepository.findById(id).orElse(null);

        // check if to do item is not null
        if (toDoItem == null) {
            throw new RuntimeException("To Do Item not found with ID: " + id);
        }

        // check if user id is not null
        if (userId == null) {
            throw new RuntimeException("User ID is required");
        }

        // check if user id is not equal to to do item user id
        if (!toDoItem.getUser().getUserID().equals(userId)) {
            throw new RuntimeException("User ID is not valid");
        }

        if (toDoItem != null) {
            toDoItem.setTitle(toDoItemViewDto.getTitle());
            toDoItem.setDescription(toDoItemViewDto.getDescription());

            // if target date is not null then set target date
            if (toDoItemViewDto.getTargetDate() != null) {
                toDoItem.setTargetDate(toDoItemViewDto.getTargetDate());
            }

            // update to do item
            toDoItemRepository.save(toDoItem);
        }
        return toDoItemViewDto;
    }

    @Override
    public String deleteToDoItem(Long id) {
        // Get to do item by id
        ToDoItem toDoItem = toDoItemRepository.findById(id).orElse(null);
        if (toDoItem != null) {
            // delete to do item
            toDoItemRepository.delete(toDoItem);
            return "To Do Item deleted successfully";
        }
        return "To Do Item not found";
    }

    @Override
    public ToDoItemDto markDoneStatus(Long id) {
        // Get to do item by id
        ToDoItem toDoItem = toDoItemRepository.findById(id).orElse(null);
        if (toDoItem != null) {
            if(toDoItem.isDone()) {
                toDoItem.setDone(false);
            } else {
                toDoItem.setDone(true);
            }

            // update to do item
            toDoItemRepository.save(toDoItem);
            return convertToDoItemToToDoItemDto(toDoItem);
        }
        return null;
    }

    // convert ToDoItem to ToDoItemDto
    private ToDoItemDto convertToDoItemToToDoItemDto(ToDoItem toDoItem) {
        ToDoItemDto toDoItemDto = new ToDoItemDto();
        toDoItemDto.setId(toDoItem.getId());
        toDoItemDto.setTitle(toDoItem.getTitle());
        toDoItemDto.setDescription(toDoItem.getDescription());
        toDoItemDto.setDone(toDoItem.isDone());
        toDoItemDto.setTargetDate(toDoItem.getTargetDate());
        return toDoItemDto;
    }
}

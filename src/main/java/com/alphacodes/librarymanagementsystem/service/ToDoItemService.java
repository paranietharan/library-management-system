package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.ToDoItemDto;
import com.alphacodes.librarymanagementsystem.DTO.ToDoItemViewDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ToDoItemService {
    List<ToDoItemDto> getAllToDoItemsByUserId(String userId);
    ToDoItemDto getToDoItemById(Long id);
    ToDoItemViewDto saveToDoItem(ToDoItemViewDto toDoItemViewDto, String userId);
    ToDoItemViewDto updateToDoItem(Long id, ToDoItemViewDto toDoItemViewDto, String userId);
    String deleteToDoItem(Long id);

    ToDoItemDto markDoneStatus(Long id);
}

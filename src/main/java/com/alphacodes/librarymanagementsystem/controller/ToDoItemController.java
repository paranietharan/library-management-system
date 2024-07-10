package com.alphacodes.librarymanagementsystem.controller;


import com.alphacodes.librarymanagementsystem.DTO.ToDoItemDto;
import com.alphacodes.librarymanagementsystem.DTO.ToDoItemViewDto;
import com.alphacodes.librarymanagementsystem.service.ToDoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/todo")
public class ToDoItemController {

    @Autowired
    private ToDoItemService toDoItemService;

    // create a new to do item
    @PostMapping("/create/{userId}")
    public ResponseEntity<ToDoItemViewDto> createToDoItem(
            @PathVariable String userId,
            @RequestBody ToDoItemViewDto toDoItemViewDto
    ) {
        return ResponseEntity.ok(toDoItemService.saveToDoItem(toDoItemViewDto, userId));
    }

    // delete a to do item
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteToDoItem(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(toDoItemService.deleteToDoItem(id));
    }

    // update a to do item
    @PutMapping("{userId}/update/{id}")
    public ResponseEntity<ToDoItemViewDto> updateToDoItem(
            @PathVariable Long id,
            @RequestBody ToDoItemViewDto toDoItemViewDto,
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(toDoItemService.updateToDoItem(id, toDoItemViewDto, userId));
    }

    // get a to do item by id
    @GetMapping("/get/{id}")
    public ResponseEntity<ToDoItemDto> getToDoItemById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(toDoItemService.getToDoItemById(id));
    }

    // get all to do items by user id
    @GetMapping("/get/user")
    public ResponseEntity<List<ToDoItemDto>> getAllToDoItemsByUserId(
            @RequestParam String userId
    ) {
        return ResponseEntity.ok(toDoItemService.getAllToDoItemsByUserId(userId));
    }

    // mark as done
    @PutMapping("/mark/{id}")
    public ResponseEntity<ToDoItemDto> markAsDone(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(toDoItemService.markDoneStatus(id));
    }
}

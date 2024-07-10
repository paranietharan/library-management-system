package com.alphacodes.librarymanagementsystem.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ToDoItemDto {
    private Long id;
    private String title;
    private String description;
    private boolean isDone;
    private Date targetDate;
}

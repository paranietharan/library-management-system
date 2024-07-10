package com.alphacodes.librarymanagementsystem.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ToDoItemViewDto {
    private String title;
    private String description;
    private Date targetDate;
}

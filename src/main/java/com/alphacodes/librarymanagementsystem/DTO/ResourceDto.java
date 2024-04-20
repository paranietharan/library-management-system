package com.alphacodes.librarymanagementsystem.DTO;

import lombok.Data;

@Data
public class ResourceDto {
    private String title;
    private String author;
    private Integer availability;
    private String category;
}

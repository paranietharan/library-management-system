package com.alphacodes.librarymanagementsystem.DTO;

import lombok.Data;

@Data
public class ResourceDto {
    private String isbn;
    private String title;
    private String author;
    private Integer no_of_copies;
    private String category;
    private String about;
    private byte[] bookImg;
}

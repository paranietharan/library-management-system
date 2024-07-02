package com.alphacodes.librarymanagementsystem.DTO;

import lombok.Data;

@Data
public class ResourceCommentDto {
    private Long resourceCommentId;
    private String userID;
    private String comment;
}

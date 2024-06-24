package com.alphacodes.librarymanagementsystem.DTO;

import lombok.Data;

@Data
public class ArticleCommentDto {
    String comment;
    int commenterId;
    int articleId;
}

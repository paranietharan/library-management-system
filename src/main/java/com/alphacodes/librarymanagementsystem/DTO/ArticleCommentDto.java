package com.alphacodes.librarymanagementsystem.DTO;

import lombok.Data;

@Data
public class ArticleCommentDto {
    String comment;
    String commenterId;
    int articleId;
    int articleCommentId;
}

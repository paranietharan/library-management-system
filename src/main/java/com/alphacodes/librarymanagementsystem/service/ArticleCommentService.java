package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.CommentDto;

import java.util.List;

public interface ArticleCommentService {
    CommentDto addArticleComment(int articleID, CommentDto commentDto);
    List<CommentDto> getAllArticleComments(int articleID);
    CommentDto getArticleCommentById(int articleID, int articleCommentId);
    String deleteArticleComment(int articleID, int articleCommentId);

}

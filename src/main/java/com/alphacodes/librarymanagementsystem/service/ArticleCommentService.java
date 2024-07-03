package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.ArticleCommentDto;
import com.alphacodes.librarymanagementsystem.Model.ArticleComment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleCommentService {
    ArticleComment addArticleComment(ArticleCommentDto articleCommentDto);
    String deleteArticleComment(int articleCommentId);
    List<ArticleCommentDto> getAllArticleComments(int articleID);
}

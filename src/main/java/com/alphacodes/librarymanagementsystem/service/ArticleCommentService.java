package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.Model.ArticleComment;

import java.util.List;

public interface ArticleCommentService {
    List<ArticleComment> getAllArticleComment(Integer articleId);
}

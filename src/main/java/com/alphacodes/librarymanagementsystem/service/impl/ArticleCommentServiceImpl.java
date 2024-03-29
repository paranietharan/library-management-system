package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.Model.ArticleComment;
import com.alphacodes.librarymanagementsystem.repository.ArticleCommentRepository;
import com.alphacodes.librarymanagementsystem.service.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ArticleCommentServiceImpl implements ArticleCommentService {
    @Autowired
    ArticleCommentRepository articleCommentRepository;

    @Override
    public List<ArticleComment> getAllArticleComment(Integer articleId) {
        return null;
    }
}

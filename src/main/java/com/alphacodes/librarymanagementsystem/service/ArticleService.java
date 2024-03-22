package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.Model.Article;

import java.util.List;

public interface ArticleService {
    void AddArticle(Article article);

    List<Article> getAllArticles();
}

package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.ArticleDto;
import com.alphacodes.librarymanagementsystem.Model.Article;

import java.util.List;

public interface ArticleService {
     ArticleDto addArticle(ArticleDto articleDto);
     String deleteArticle(int articleID);
     ArticleDto getArticleById(int articleID);
    List<ArticleDto> getAllArticles();
}

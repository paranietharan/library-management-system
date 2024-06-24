package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.ArticleDto;
import com.alphacodes.librarymanagementsystem.DTO.ArticleViewDto;
import com.alphacodes.librarymanagementsystem.Model.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
     ArticleDto addArticle(ArticleDto articleDto);
     String deleteArticle(int articleID);
     ArticleDto getArticleById(int articleID);
    List<ArticleDto> getAllArticles();


    // For article view dto
    List<ArticleViewDto> getAllArticleView();
    ArticleViewDto getArticleViewById(int articleId);

    Article getArticleFullById(int articleId);
}

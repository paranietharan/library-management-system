package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.ArticleDto;
import com.alphacodes.librarymanagementsystem.Model.Article;
import com.alphacodes.librarymanagementsystem.repository.ArticleRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ArticleDto addArticle(ArticleDto articleDto) {
        Article article = mapToArticle(articleDto);
        Article newArticle = articleRepository.save(article);
        return mapToArticleDto(newArticle);
    }

    @Override
    public String deleteArticle(int articleID) {
        articleRepository.deleteById(articleID);
        return "Article deleted Successfully";
    }

    @Override
    public ArticleDto getArticleById(int articleID) {
        Article article = articleRepository.findById(articleID).
                orElseThrow(() -> new RuntimeException("Article not found with id " + articleID));
        return mapToArticleDto(article);
    }

    @Override
    public List<ArticleDto> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(this::mapToArticleDto).collect(Collectors.toList());
    }

    private ArticleDto mapToArticleDto(Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setUserID(article.getAuthor().getUserID());
        articleDto.setTitle(article.getTitle());
        articleDto.setBody(article.getBody());
        return articleDto;
    }

    private Article mapToArticle(ArticleDto articleDto) {
        Article article = new Article();
        article.setAuthor(userRepository.findById(articleDto.getUserID()).orElseThrow(
                () -> new RuntimeException("User not found with id " + articleDto.getUserID())));
        article.setTitle(articleDto.getTitle());
        article.setBody(articleDto.getBody());
        return article;
    }

}






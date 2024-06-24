package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.ArticleDto;
import com.alphacodes.librarymanagementsystem.DTO.ArticleViewDto;
import com.alphacodes.librarymanagementsystem.Model.Article;
import com.alphacodes.librarymanagementsystem.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/add")
    public ResponseEntity<ArticleDto> addArticle(@RequestBody ArticleDto articleDto) {
        try {
            ArticleDto addedArticle = articleService.addArticle(articleDto);
            return ResponseEntity.ok(addedArticle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        try {
            List<ArticleDto> articles = articleService.getAllArticles();
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{articleID}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable int articleID) {
        try {
            ArticleDto article = articleService.getArticleById(articleID);
            return ResponseEntity.ok(article);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{articleID}")
    public ResponseEntity<String> deleteArticle(@PathVariable int articleID) {
        try {
            String result = articleService.deleteArticle(articleID);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // for article view dto
    @GetMapping("/allArticles")
    public List<ArticleViewDto> getAllArticleView() {
        return articleService.getAllArticleView();
    }

    @GetMapping("view/{articleId}")
    public ArticleViewDto getArticleViewById(@PathVariable int articleId) {
        return articleService.getArticleViewById(articleId);
    }

    @GetMapping("viewFull/{articleId}")
    public Article getFullArticleById(@PathVariable int articleId) {
        return articleService.getArticleFullById(articleId);
    }

}

package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.ArticleDto;
import com.alphacodes.librarymanagementsystem.service.ArticleService;
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
        return ResponseEntity.ok(articleService.addArticle(articleDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @GetMapping("/{articleID}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable int articleID) {
        return ResponseEntity.ok(articleService.getArticleById(articleID));
    }

    @DeleteMapping("/{articleID}")
    public ResponseEntity<String> deleteArticle(@PathVariable int articleID) {
        return ResponseEntity.ok(articleService.deleteArticle(articleID));
    }

}

package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.Model.Article;
import com.alphacodes.librarymanagementsystem.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/add")
    public String addArticle(@RequestBody Article article){
        articleService.AddArticle(article);
        return "Success";
    }

    @GetMapping(value="/articles")
    public List<Article> posts(){
        return articleService.getAllArticles();
    }
}

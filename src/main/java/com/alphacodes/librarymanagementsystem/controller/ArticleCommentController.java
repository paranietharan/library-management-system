package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.ArticleCommentDto;
import com.alphacodes.librarymanagementsystem.Model.ArticleComment;
import com.alphacodes.librarymanagementsystem.service.ArticleCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleCommentController {
    private final ArticleCommentService articleCommentService;

    public ArticleCommentController(ArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
    }

    @PostMapping("/{articleId}/comment")
    public ResponseEntity<ArticleComment> addArticleComment(@RequestBody ArticleCommentDto articleCommentDTO) {
        ArticleComment newArticleComment = articleCommentService.addArticleComment(articleCommentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newArticleComment);
    }

    @GetMapping("/{articleId}/comment")
    public List<ArticleCommentDto> getAllArticleComments(@PathVariable int articleId) {
        return articleCommentService.getAllArticleComments(articleId);
    }

    @DeleteMapping("/comment/{articleCommentId}")
    public ResponseEntity<String> deleteArticleComment( @PathVariable int articleCommentId) {
        return new ResponseEntity<>(articleCommentService.deleteArticleComment(articleCommentId), HttpStatus.NO_CONTENT);
    }

}

package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.CommentDto;
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

    // Add a new comment to an article
    @PostMapping("/{articleId}/comment")
    public ResponseEntity<CommentDto> addArticleComment(@PathVariable int articleId, @RequestBody CommentDto CommentDto) {
        return new ResponseEntity<>(articleCommentService.addArticleComment(articleId, CommentDto), HttpStatus.CREATED);
    }

    // Get all comments for an article
    @GetMapping("/{articleId}/comment")
    public List<CommentDto> getAllArticleComments(@PathVariable int articleId) {
        return articleCommentService.getAllArticleComments(articleId);
    }

    // Get a comment by its ID
    @GetMapping("/{articleId}/comment/{articleCommentId}")
    public ResponseEntity<CommentDto> getArticleCommentById(@PathVariable int articleCommentId, @PathVariable int articleId) {
        CommentDto commentDto = articleCommentService.getArticleCommentById(articleId,articleCommentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    // Delete a comment by its ID
    @DeleteMapping("/{articleId}/comment/{articleCommentId}")
    public ResponseEntity<String> deleteArticleComment(@PathVariable int articleCommentId, @PathVariable int articleId) {
        return new ResponseEntity<>(articleCommentService.deleteArticleComment(articleId, articleCommentId),HttpStatus.NO_CONTENT);
    }
}

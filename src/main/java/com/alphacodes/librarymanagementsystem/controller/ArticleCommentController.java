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
    @PostMapping("/{aID}/comment")
    public ResponseEntity<CommentDto> addArticleComment(@PathVariable int aID, @RequestBody CommentDto CommentDto) {
        return new ResponseEntity<>(articleCommentService.addArticleComment(aID, CommentDto), HttpStatus.CREATED);
    }

    // Get all comments for an article
    @GetMapping("/{aID}/comment")
    public List<CommentDto> getAllArticleComments(@PathVariable int aID) {
        return articleCommentService.getAllArticleComments(aID);
    }

    // Get a comment by its ID
    @GetMapping("/{aID}/comment/{acmID}")
    public ResponseEntity<CommentDto> getArticleCommentById(@PathVariable int acmID, @PathVariable int aID) {
        CommentDto commentDto = articleCommentService.getArticleCommentById(aID,acmID);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    // Delete a comment by its ID
    @DeleteMapping("/{aID}/comment/{acmID}")
    public ResponseEntity<String> deleteArticleComment(@PathVariable int acmID, @PathVariable int aID) {
        return new ResponseEntity<>(articleCommentService.deleteArticleComment(aID, acmID),HttpStatus.NO_CONTENT);
    }
}

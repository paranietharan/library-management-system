package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.RatingDto;
import com.alphacodes.librarymanagementsystem.service.ArticleRatingService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleRatingController {

    private final ArticleRatingService articleRatingService;

    public ArticleRatingController(ArticleRatingService articleRatingService){
        this.articleRatingService = articleRatingService;
    }

    // Add a new rating to an article
    @PostMapping("/{articleID}/rating")
    public ResponseEntity<RatingDto> addArticleRating(@PathVariable int articleID, @RequestBody RatingDto RatingDto) {
        return new ResponseEntity<>(articleRatingService.addOrUpdateArticleRating(articleID, RatingDto), HttpStatus.CREATED);
    }

    // Get the rating for an article
    @GetMapping("/{articleID}/rating")
    public float getArticleRating(@PathVariable int articleID) {return articleRatingService.getArticleRating(articleID);}

    // Get article rating by user Id and article Id
    @GetMapping("/{articleID}/rating/{userId}")
    public ResponseEntity<Float> getArticleRatingByUserId(@PathVariable int articleID, @PathVariable String userId) {
        try {
            float rating = articleRatingService.getArticleRatingByUserId(articleID, userId);
            return ResponseEntity.ok(rating);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Handle other exceptions
        }
    }
}

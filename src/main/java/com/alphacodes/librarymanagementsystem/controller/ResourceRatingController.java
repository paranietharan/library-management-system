package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.RatingDto;
import com.alphacodes.librarymanagementsystem.service.ResourceRatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resource")

public class ResourceRatingController {
    private final ResourceRatingService resourceRatingService;

    public ResourceRatingController(ResourceRatingService resourceRatingService) {
        this.resourceRatingService = resourceRatingService;
    }

    @PostMapping("/{resourceId}/rating")
    public ResponseEntity<RatingDto> addOrUpdateArticleRating(
            @PathVariable Long resourceId,
            @RequestBody RatingDto ratingDto
    ) {
        return new ResponseEntity<>(
                resourceRatingService
                        .addOrUpdateResourceRating(resourceId, ratingDto), HttpStatus.CREATED
        );
    }

    // Get the rating for a resource
    @GetMapping("/{resourceId}/rating")
    public float getResourceRating(@PathVariable Long resourceId) {
        return resourceRatingService.getResourceRating(resourceId);
    }

    //
    @GetMapping("/{articleID}/rating/{userId}")
    public ResponseEntity<Float> getResourceRatingByUserId(
            @PathVariable Long articleID,
            @PathVariable String userId
    ) {
        return new ResponseEntity<>(
                resourceRatingService.getResourceRatingByUserId(articleID, userId), HttpStatus.OK
        );
    }
}

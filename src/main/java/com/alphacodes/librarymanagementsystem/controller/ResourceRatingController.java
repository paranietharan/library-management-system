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

    // Add a new rating to a resource
    @PostMapping("/{resourceId}/rating")
    public ResponseEntity<RatingDto> addResourceRating(@PathVariable Long resourceId, @RequestBody RatingDto RatingDto) {
        return new ResponseEntity<>(resourceRatingService.addResourceRating(resourceId, RatingDto), HttpStatus.CREATED);
    }

    // Get the rating for a resource
    @GetMapping("/{resourceId}/rating")
    public float getResourceRating(@PathVariable Long resourceId) {
        return resourceRatingService.getResourceRating(resourceId);
    }
}

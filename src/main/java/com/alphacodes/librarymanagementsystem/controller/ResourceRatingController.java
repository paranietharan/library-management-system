package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.ResourceRatingDto;
import com.alphacodes.librarymanagementsystem.service.ResourceRatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/{userID}/resource")

public class ResourceRatingController {
    private final ResourceRatingService resourceRatingService;

    public ResourceRatingController(ResourceRatingService resourceRatingService) {
        this.resourceRatingService = resourceRatingService;
    }

    // Add a new rating to a resource
    @PostMapping("/{rID}/rating")
    public ResponseEntity<ResourceRatingDto> addResourceRating(@PathVariable int userID, @PathVariable Long rID, @RequestBody ResourceRatingDto resourceRatingDto) {
        return new ResponseEntity<>(resourceRatingService.addResourceRating(userID, rID, resourceRatingDto), HttpStatus.CREATED);
    }

    // Get the rating for a resource
    @GetMapping("/{rID}/rating")
    public float getResourceRating(@PathVariable Long rID) {
        return resourceRatingService.getResourceRating(rID);
    }
}

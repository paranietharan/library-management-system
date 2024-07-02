package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.RatingDto;

public interface ResourceRatingService {
    float getResourceRating(Long resourceId);
    RatingDto addOrUpdateResourceRating(Long resourceId, RatingDto ratingDto);

    Float getResourceRatingByUserId(Long articleID, String userId);
}

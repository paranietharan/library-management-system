package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.RatingDto;

public interface ResourceRatingService {
    RatingDto addResourceRating(Long resourceId, RatingDto ratingDto);
    float getResourceRating(Long resourceId);

}

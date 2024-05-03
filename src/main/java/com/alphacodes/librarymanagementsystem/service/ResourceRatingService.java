package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.RatingDto;

public interface ResourceRatingService {
    RatingDto addResourceRating(Long rID, RatingDto ratingDto);
    float getResourceRating(Long rID);

}

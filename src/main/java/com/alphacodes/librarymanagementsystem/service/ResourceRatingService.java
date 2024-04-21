package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.ResourceRatingDto;

public interface ResourceRatingService {
    ResourceRatingDto addResourceRating(int userID, Long rID, ResourceRatingDto rating);
    float getResourceRating(Long rID);

}

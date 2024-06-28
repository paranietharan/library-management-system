package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.RatingDto;

public interface ArticleRatingService {
    RatingDto addOrUpdateArticleRating(int articleID, RatingDto ratingDto);
    float getArticleRating(int articleID);

    float getArticleRatingByUserId(int articleID, String userId);
}

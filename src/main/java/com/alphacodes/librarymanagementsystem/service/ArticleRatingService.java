package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.RatingDto;

public interface ArticleRatingService {
    RatingDto addArticleRating(int articleID, RatingDto ratingDto);
    float getArticleRating(int articleID);
}

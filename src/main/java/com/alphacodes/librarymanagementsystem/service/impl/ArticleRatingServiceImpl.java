package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.RatingDto;
import com.alphacodes.librarymanagementsystem.Model.ArticleRating;
import com.alphacodes.librarymanagementsystem.repository.ArticleRatingRepository;
import com.alphacodes.librarymanagementsystem.repository.ArticleRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.ArticleRatingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleRatingServiceImpl implements ArticleRatingService {

    private final ArticleRatingRepository articleRatingRepository;

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    public ArticleRatingServiceImpl(ArticleRatingRepository articleRatingRepository, ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRatingRepository = articleRatingRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RatingDto addArticleRating(int articleID, RatingDto ratingDto) {
        ArticleRating articleRating = convertToArticleRating(ratingDto);
        articleRating.setArticle(articleRepository.findById(articleID).orElseThrow(
            () -> new RuntimeException("Article not found with id " + articleID)));

        ArticleRating newArticleRating = articleRatingRepository.save(articleRating);
        return convertToRatingDto(newArticleRating);
    }

    @Override
    public float getArticleRating(int articleID) {return calculateArticleRating(articleID);}

    private float calculateArticleRating(int articleID) {
        List<ArticleRating> ratings = articleRatingRepository.findByArticle(articleRepository.findById(articleID).orElseThrow(
            () -> new RuntimeException("Article not found with id " + articleID)));

        if (ratings.isEmpty()) {
            throw new RuntimeException("No ratings found for article with id " + articleID);
        }

        float sum = 0;
        for (ArticleRating rating : ratings) {
            sum += rating.getRating();
        }

        return sum / ratings.size();
    }

    private ArticleRating convertToArticleRating(RatingDto ratingDto) {
        ArticleRating articleRating = new ArticleRating();
        articleRating.setCommenter(userRepository.findById(ratingDto.getUserID()).orElseThrow(
            () -> new RuntimeException("User not found with id " + ratingDto.getUserID())));
        articleRating.setRating(ratingDto.getRating());
        return articleRating;
    }

    private RatingDto convertToRatingDto(ArticleRating articleRating) {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setRating(articleRating.getRating());
        ratingDto.setUserID(articleRating.getCommenter().getUserID());
        return ratingDto;
    }
}

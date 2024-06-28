package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.RatingDto;
import com.alphacodes.librarymanagementsystem.Model.Article;
import com.alphacodes.librarymanagementsystem.Model.ArticleRating;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.repository.ArticleRatingRepository;
import com.alphacodes.librarymanagementsystem.repository.ArticleRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.ArticleRatingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public RatingDto addOrUpdateArticleRating(int articleID, RatingDto ratingDto) {
        Article article = articleRepository.findById(articleID).orElseThrow(
                () -> new RuntimeException("Article not found with id " + articleID));

        // get user details
        User user = userRepository.findByUserID(ratingDto.getUserID());

        Optional<ArticleRating> existingRating = articleRatingRepository.findByArticleAndCommenter(article, user);
        ArticleRating articleRating;

        if (existingRating.isPresent()) {
            articleRating = existingRating.get();
            articleRating.setRating(ratingDto.getRating());
        } else {
            articleRating = new ArticleRating();
            articleRating.setArticle(article);
            articleRating.setCommenter(user);
            articleRating.setRating(ratingDto.getRating());
        }

        ArticleRating savedRating = articleRatingRepository.save(articleRating);
        return convertToRatingDto(savedRating);
    }

    @Override
    public float getArticleRating(int articleID) {return calculateArticleRating(articleID);}

    @Override
    public float getArticleRatingByUserId(int articleID, String userId) {
        Optional<ArticleRating> optionalRating = articleRatingRepository.findByArticleAndCommenter(
                articleRepository.findById(articleID).orElseThrow(
                        () -> new RuntimeException("Article not found with id " + articleID)),
                userRepository.findByUserID(userId));

        if (optionalRating.isPresent()) {
            ArticleRating articleRating = optionalRating.get();
            float rating = articleRating.getRating();
            return rating;
        } else {
            throw new RuntimeException("Rating not found for article with id " + articleID + " and user with id " + userId);
        }
    }


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
        articleRating.setCommenter(userRepository.findByUserID(ratingDto.getUserID()));
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

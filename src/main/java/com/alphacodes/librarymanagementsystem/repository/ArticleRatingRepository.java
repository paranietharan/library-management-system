package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.Article;
import com.alphacodes.librarymanagementsystem.Model.ArticleRating;
import com.alphacodes.librarymanagementsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRatingRepository extends JpaRepository<ArticleRating, Integer> {
    List<ArticleRating> findByArticle(Article article);
    Optional<ArticleRating> findByArticleAndCommenter(Article article, User commenter);

    // find all ratings by a user
    @Query("SELECT ar FROM ArticleRating ar WHERE ar.commenter = ?1")
    Iterable<ArticleRating> findByUser(User user);
}

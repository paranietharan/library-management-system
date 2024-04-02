package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;

@Entity
public class ArticleRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int articleRatingId;
    @ManyToOne
    @JoinColumn(name = "articles", nullable = false)
    Article article;
    @ManyToOne
    @JoinColumn(name = "member", nullable = false)
    User commenter;
    float rating;
}

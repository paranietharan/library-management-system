package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "article_comments")
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int articleCommentId;
    @ManyToOne
    @JoinColumn(name = "articles", nullable = false)
    Article article;
    @ManyToOne
    @JoinColumn(name = "member", nullable = false)
    Member commenter;
    String comment;
}

package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

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
    User commenter;

    String comment;
}

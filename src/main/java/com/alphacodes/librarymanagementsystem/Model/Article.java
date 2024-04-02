package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int articleId;
    String title;
    String body;
    private Date dateCreated;
    @ManyToOne
    @JoinColumn(name = "members", nullable = false)
    User author;
}

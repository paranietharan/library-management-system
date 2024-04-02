package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;

@Entity
public class ArticleManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amaID;

    @ManyToOne
    @JoinColumn(name = "amlID")
    private User librarian;

    private String caption;
}


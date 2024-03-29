package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;

@Entity
public class ResourceRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long armID;

    @ManyToOne
    @JoinColumn(name = "arrID")
    private Resource resource;

    private int rating;
}


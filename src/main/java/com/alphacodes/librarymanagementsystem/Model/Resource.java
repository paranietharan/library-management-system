package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rID;
    private String title;
    private String author;
    private boolean availability;
    private String category;

    // Getters and setters
}


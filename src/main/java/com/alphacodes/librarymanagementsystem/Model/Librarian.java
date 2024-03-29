package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;

@Entity
public class Librarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lID;

    @ManyToOne
    @JoinColumn(name = "LUserName")
    private Account account;

    // Getters and setters
}


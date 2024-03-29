package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;

@Entity
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fmID;

    @ManyToOne
    @JoinColumn(name = "flID")
    private Librarian librarian;

    private double amount;
    private String status;
}


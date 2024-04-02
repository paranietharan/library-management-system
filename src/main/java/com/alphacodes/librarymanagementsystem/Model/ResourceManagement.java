package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;

@Entity
public class ResourceManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rmaID;

    @ManyToOne
    @JoinColumn(name = "rmlID")
    private User librarian;

    private String caption;
}


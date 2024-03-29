package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;

@Entity
public class ResourceComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rcmID;

    @ManyToOne
    @JoinColumn(name = "rcrID")
    private Resource resource;

    private String comment;
}


package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reID;

    @ManyToOne
    @JoinColumn(name = "rmID")
    private Resource resource;

    @ManyToOne
    @JoinColumn(name = "rrID")
    private Resource reservationResource;
}


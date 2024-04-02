package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;

@Entity
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cID;
    private String complaint;

    @ManyToOne
    @JoinColumn(name = "cmID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "clID")
    private Chat chat;
}


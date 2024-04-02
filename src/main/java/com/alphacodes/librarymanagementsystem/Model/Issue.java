package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imID;

    @ManyToOne
    @JoinColumn(name = "irID")
    private Resource resource;

    @ManyToOne
    @JoinColumn(name = "ilID")
    private User librarian;

    private Date date;
}


package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;

@Entity
public class AccountManagement {
    @Id
    private String acmauserName;

    @ManyToOne
    @JoinColumn(name = "acmlID")
    private Librarian librarian;

    private String caption;
}


package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imID;
    private Date date;

    @OneToOne
    @JoinColumn(name = "book", nullable = false)
    private Resource book;

    @OneToOne
    @JoinColumn(name = "member", nullable = false)
    private User member;

    @OneToOne
    @JoinColumn(name = "librarian", nullable = false)
    private User librarian;
}


package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fineId;
    private double amount;
    private String status;

    @ManyToOne
    @JoinColumn(name = "member", nullable = false)
    private User member;

    @ManyToOne
    @JoinColumn(name = "librarian", nullable = false)
    private User librarian;
}


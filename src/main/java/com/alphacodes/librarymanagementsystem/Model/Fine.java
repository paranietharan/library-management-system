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
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fineId;
    private double amount;
    private boolean paidStatus;
    private Date resourceIssueDate;

    @ManyToOne
    @JoinColumn(name = "member", nullable = false)
    private User member;

    @OneToOne
    @JoinColumn(name = "issue", nullable = false)
    private Issue issue;
}


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
    private Long issueId;
    private Date date;
    private boolean returned;
    private boolean finePaid;

    @ManyToOne
    @JoinColumn(name = "book", nullable = false)
    private Resource book;

    @ManyToOne
    @JoinColumn(name = "member", nullable = false)
    private User member;
}

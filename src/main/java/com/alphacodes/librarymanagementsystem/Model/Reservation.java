package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    private String status;

    @ManyToOne
    @JoinColumn(name = "member", nullable = false)
    private User member;

    @ManyToOne
    @JoinColumn(name = "book", nullable = false)
    private Resource book;

    private LocalDateTime reservationTime;
}


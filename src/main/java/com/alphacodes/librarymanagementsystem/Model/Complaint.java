package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complaintId;

    private String complaintType;
    private String complaintDescription;
    private boolean isResolved;
    private String complaintDate;
    private String complaintTime;

    @ManyToOne
    @JoinColumn(name = "member", nullable = false)
    private User member;
    //TODO private int userID ;

}


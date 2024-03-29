package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int memberID;
    String firstName;
    String lastName;
    String phoneNumber;
    String emailAddress;
    String password;
    @OneToMany
    private List<Article> articles;

    @OneToMany
    private List<Complaint> complaints;
}

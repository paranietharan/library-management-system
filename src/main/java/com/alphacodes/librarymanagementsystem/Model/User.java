package com.alphacodes.librarymanagementsystem.Model;

import com.alphacodes.librarymanagementsystem.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int memberID;
    String firstName;
    String lastName;
    String phoneNumber;
    String emailAddress;
    String password;

    @Enumerated(EnumType.STRING)
    Role role;

    @OneToMany
    private List<Article> articles;

    @OneToMany
    private List<Complaint> complaints;

    public User orElse(Object o) {
        return null;
    }
}
package com.alphacodes.librarymanagementsystem.Model;

import com.alphacodes.librarymanagementsystem.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
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
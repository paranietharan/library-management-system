package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Student {
    @Id
    private String indexNumber;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String grade;
    private Date dateOfBirth;
}

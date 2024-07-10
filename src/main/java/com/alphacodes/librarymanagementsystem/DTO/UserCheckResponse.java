package com.alphacodes.librarymanagementsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCheckResponse {
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String grade;
    private String message;
}

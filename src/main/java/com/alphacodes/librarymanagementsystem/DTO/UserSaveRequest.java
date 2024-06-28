package com.alphacodes.librarymanagementsystem.DTO;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveRequest {
    private String firstName;
    private String lastName;
    private String indexNumber;
    private String emailAddress;
    private String phoneNumber;
    private String password;

}


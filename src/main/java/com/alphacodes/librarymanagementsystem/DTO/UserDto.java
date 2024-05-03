package com.alphacodes.librarymanagementsystem.DTO;

import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.enums.Role;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private String password;
    private Role role;

    public User toUser() {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .emailAddress(emailAddress)
                .password(password)
                .role(role)
                .build();
    }
}


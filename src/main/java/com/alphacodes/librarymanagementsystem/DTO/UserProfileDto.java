package com.alphacodes.librarymanagementsystem.DTO;

import com.alphacodes.librarymanagementsystem.enums.Role;
import lombok.Data;

@Data
public class UserProfileDto {
    private String userID;
    private String firstName;
    private String lastName;
    private Role role;
    private byte[] profileImg;
}
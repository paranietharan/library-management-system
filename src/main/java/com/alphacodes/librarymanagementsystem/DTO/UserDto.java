package com.alphacodes.librarymanagementsystem.DTO;

import com.alphacodes.librarymanagementsystem.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private String userID;
    private String firstName;
    private String email;
    private String phoneNumber;
    private String lastName;
    private byte[] profileImg;
}

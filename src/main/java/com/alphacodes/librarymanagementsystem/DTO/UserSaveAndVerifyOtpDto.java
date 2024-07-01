package com.alphacodes.librarymanagementsystem.DTO;

import lombok.Data;

@Data
public class UserSaveAndVerifyOtpDto {
    private String firstName;
    private String lastName;
    private String indexNumber;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    private String otpValue;
}

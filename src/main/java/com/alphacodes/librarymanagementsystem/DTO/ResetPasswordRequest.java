package com.alphacodes.librarymanagementsystem.DTO;


import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String emailAddress;
    private String Password;
    private Boolean isVerified;
}

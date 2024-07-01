package com.alphacodes.librarymanagementsystem.DTO;

import lombok.Data;

@Data
public class VerifyOtpDto {
    private String emailAddress;
    private String otpValue;

}

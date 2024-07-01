package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.EmailDto;
import com.alphacodes.librarymanagementsystem.DTO.VerifyOtpDto;
import com.alphacodes.librarymanagementsystem.EmailService.EmailServiceImpl;
import com.alphacodes.librarymanagementsystem.OTPservice.OTPServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OTPController {

    @Autowired
    private OTPServiceImpl otpService;

    @Autowired
    private EmailServiceImpl emailService;

    // Generate OTP
    @PostMapping("/generate")
    public String generateOTP(@RequestBody EmailDto emailDto) {
        OTPServiceImpl otpService = new OTPServiceImpl();
        String otp = otpService.generateOTP(emailDto.getEmailAddress());
        otpService.storeOTP(emailDto.getEmailAddress(), otp);
       // emailService.sendOTP(emailDto.getEmailAddress(), otp);
        return "OTP sent to your email";
    }

    // Verify OTP
    @PostMapping("/verify")
    public String verifyOTP(@RequestBody VerifyOtpDto verifyOtpDto) {
        boolean isValid = otpService.verifyOTP(verifyOtpDto);
        return isValid ? "OTP verified successfully" : "Invalid or expired OTP";
    }
}

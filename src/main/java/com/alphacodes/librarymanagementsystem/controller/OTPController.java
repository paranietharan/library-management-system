package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.EmailService.EmailServiceImpl;
import com.alphacodes.librarymanagementsystem.OTPservice.OTPServiceImpl;
import com.alphacodes.librarymanagementsystem.util.OTPGenerator;
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
    public String generateOTP(@RequestParam String email) {
        String otp = OTPGenerator.generateOTP();
        otpService.storeOTP(email, otp);
        emailService.sendOTP(email, otp);
        return "OTP sent to your email";
    }

    // Verify OTP
    @PostMapping("/verify")
    public String verifyOTP(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = otpService.verifyOTP(email, otp);
        return isValid ? "OTP verified successfully" : "Invalid or expired OTP";
    }
}

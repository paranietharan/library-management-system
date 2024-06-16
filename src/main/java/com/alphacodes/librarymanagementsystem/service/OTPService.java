package com.alphacodes.librarymanagementsystem.service;

public interface OTPService {
    public void storeOTP(String email, String otp);
    public boolean verifyOTP(String email, String otp);
}

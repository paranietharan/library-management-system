package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.service.OTPService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OTPServiceImpl implements OTPService {

    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private final Map<String, Long> otpTimestamps = new ConcurrentHashMap<>();
    private static final long OTP_EXPIRATION_TIME = 5 * 60 * 1000; // 5 minutes

    public void storeOTP(String email, String otp) {
        // Store OTP and timestamp
        otpStorage.put(email, otp);
        // Store the timestamp when the OTP was generated
        otpTimestamps.put(email, System.currentTimeMillis());
    }

    public boolean verifyOTP(String email, String otp) {
        if (!otpStorage.containsKey(email)) { // Check if OTP exists
            return false;
        }

        // Check if OTP has expired
        long timestamp = otpTimestamps.get(email);

        // If the OTP has expired, remove it from the storage
        if (System.currentTimeMillis() - timestamp > OTP_EXPIRATION_TIME) {
            otpStorage.remove(email);
            otpTimestamps.remove(email);
            return false;
        }

        // Verify the OTP
        return otp.equals(otpStorage.get(email));
    }
}

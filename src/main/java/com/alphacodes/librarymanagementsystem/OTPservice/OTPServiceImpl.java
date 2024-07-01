package com.alphacodes.librarymanagementsystem.OTPservice;

import com.alphacodes.librarymanagementsystem.DTO.VerifyOtpDto;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OTPServiceImpl {

    private static final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private static final Map<String, Long> otpTimestamps = new ConcurrentHashMap<>();
    private static final long OTP_EXPIRATION_TIME = 5 * 60 * 1000; // 5 minutes

    // OTP generation constants
    private static final String CHARACTERS = "0123456789";
    // OTP length is 6
    private static final int OTP_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();// SecureRandom is preferred for OTP generation

    public String generateOTP(String email) {
        // Generate OTP using SecureRandom
        StringBuilder otp = new StringBuilder(OTP_LENGTH);

        // Generate OTP of length 6
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        storeOTP(email, otp.toString());
        System.out.println("email: " + email + " OTP: " + otp.toString());
        System.out.println("OTP: " + otpStorage.get(email));
        return otp.toString();
    }

    public void storeOTP(String email, String otp) {
        // Store OTP and timestamp
        otpStorage.put(email, otp);
        // Store the timestamp when the OTP was generated
        otpTimestamps.put(email, System.currentTimeMillis());
    }

    public boolean verifyOTP(VerifyOtpDto verifyOtpDto) {
        System.out.println("storedOtp: " + otpStorage.get(verifyOtpDto.getEmailAddress()));
        if (!otpStorage.containsKey(verifyOtpDto.getEmailAddress())) { // Check if OTP exists
            System.out.println("OTP does not exist");
            return false;
        }

        // Check if OTP has expired
        long timestamp = otpTimestamps.get(verifyOtpDto.getEmailAddress());

        // If the OTP has expired, remove it from the storage
        if (System.currentTimeMillis() - timestamp > OTP_EXPIRATION_TIME) {
            System.out.println("OTP has expired");
            otpStorage.remove(verifyOtpDto.getEmailAddress());
            otpTimestamps.remove(verifyOtpDto.getEmailAddress());
            return false;
        }
        System.out.println("OTP: " + otpStorage.get(verifyOtpDto.getEmailAddress()));

        // Verify the OTP
        return verifyOtpDto.getOtpValue().equals(otpStorage.get(verifyOtpDto.getEmailAddress()));
    }
}

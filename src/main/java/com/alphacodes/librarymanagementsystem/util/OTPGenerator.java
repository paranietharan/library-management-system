package com.alphacodes.librarymanagementsystem.util;

import java.security.SecureRandom;

public class OTPGenerator {

    // OTP generation constants
    private static final String CHARACTERS = "0123456789";
    // OTP length is 6
    private static final int OTP_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();// SecureRandom is preferred for OTP generation

    public static String generateOTP() {
        // Generate OTP using SecureRandom
        StringBuilder otp = new StringBuilder(OTP_LENGTH);

        // Generate OTP of length 6
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return otp.toString();
    }
}

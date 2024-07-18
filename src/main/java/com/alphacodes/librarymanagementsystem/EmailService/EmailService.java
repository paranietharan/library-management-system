package com.alphacodes.librarymanagementsystem.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // true indicates HTML

            helper.setFrom("your-email@example.com");

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // or handle it in a better way
        }
    }

    // Send OTP to the user
    public void sendOTP(String to, String otp) {
        sendSimpleEmail(
                to,
                "Your OTP Code for Library Management System Account Verfication",
                "Your OTP code is: " + otp + ". Use this code to verify your account."
        );
    }
}

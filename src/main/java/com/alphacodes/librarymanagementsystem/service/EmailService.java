package com.alphacodes.librarymanagementsystem.service;

public interface EmailService {
    public void sendSimpleEmail(String to, String subject, String text);
}

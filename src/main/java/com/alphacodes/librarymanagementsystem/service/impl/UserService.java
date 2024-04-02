package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.LoginResponse;
import com.alphacodes.librarymanagementsystem.JwtAuthenticationConfig.JWTauthentication;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; // Autowire BCryptPasswordEncoder
@Autowired
    private JWTauthentication jwtA;

    public LoginResponse performlogin(String userEmailAddress, String password) {
        User user = userRepository.findByEmailAddress(userEmailAddress);
        if (user == null) {
            return new LoginResponse("User not found", false, null);
        }
        boolean isPasswordMatched = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (isPasswordMatched) {
            String token = jwtA.generateToken(user);
            return new LoginResponse("Login Successful", true, token);
        } else {
            return new LoginResponse("Incorrect Password", false, null);
        }
    }

    public User saveDetails(User user) {
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    public List<User> getAllUserDetails() {
        return userRepository.findAll();
    }
}

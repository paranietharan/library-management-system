package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.LoginResponse;
import com.alphacodes.librarymanagementsystem.DTO.UserDto;
import com.alphacodes.librarymanagementsystem.JwtAuthenticationConfig.JWTauthentication;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTauthentication jwtA;


    public UserServiceImpl(UserRepository userRepository , BCryptPasswordEncoder bCryptPasswordEncoder, JWTauthentication jwtA) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtA = jwtA;
    }


    @Override
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

    @Override
    public User createNewUser(User user) {
        return userRepository.save(user);
    }
    //TODO: http response status code

   @Override
    public User saveDetails(UserDto userDto) {
        User user = userDto.toUser();
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }
    //TODO: return type

    @Override
    public List<User> getAllUserDetails() {
        return userRepository.findAll();
    }
}

package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.LoginResponse;
import com.alphacodes.librarymanagementsystem.DTO.UserDto;
import com.alphacodes.librarymanagementsystem.Model.User;

import java.util.List;

public interface UserService {
    User saveDetails(UserDto userDto);
    List<User> getAllUserDetails();
    LoginResponse performlogin(String email, String password);
}

package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.*;
import com.alphacodes.librarymanagementsystem.Model.User;

import java.util.List;

public interface UserService {
    Boolean saveDetails(User user);
    List<User> getAllUserDetails();
    LoginResponse performLogin(LoginRequest loginRequest);
    Boolean sendOtp(String email);
    Boolean changePassword(String email, String password);
    UserCheckResponse checkDetails(UserSaveRequest userSaveRequest);
    UserProfileDto getUserProfileById(String id);
}

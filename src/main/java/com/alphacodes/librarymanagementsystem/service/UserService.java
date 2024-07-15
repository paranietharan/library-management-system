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
    UserDto updateUserProfile(String id, UserDto userDto);
    UserDto getUserProfileDetails(String id);
    Boolean deleteUserProfile(String id);

    String LibrarianAddUser(String email, String phoneNumber, String indexNumber);
    void createAdminAccount();
}

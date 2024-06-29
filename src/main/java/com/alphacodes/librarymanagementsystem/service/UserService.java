package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.*;
import com.alphacodes.librarymanagementsystem.Model.Student;
import com.alphacodes.librarymanagementsystem.Model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserSaveResponse saveDetails(UserSaveRequest userSaveRequest, Student student);
    List<User> getAllUserDetails();
    ResponseEntity<LoginResponse> performLogin(LoginRequest loginRequest);
    boolean forgotPassword(String email);
    boolean changePassword(String email, String password);

    UserProfileDto getUserProfileById(String id);
}

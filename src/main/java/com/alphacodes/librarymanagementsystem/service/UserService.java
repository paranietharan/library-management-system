package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.LoginResponse;
import com.alphacodes.librarymanagementsystem.DTO.UserProfileDto;
import com.alphacodes.librarymanagementsystem.DTO.UserSaveRequest;
import com.alphacodes.librarymanagementsystem.DTO.UserSaveResponse;
import com.alphacodes.librarymanagementsystem.Model.Student;
import com.alphacodes.librarymanagementsystem.Model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserSaveResponse saveDetails(UserSaveRequest userSaveRequest, Student student);
    List<User> getAllUserDetails();
    ResponseEntity<LoginResponse> performLogin(String email, String password);
    boolean forgotPassword(String email);
    boolean changePassword(String email, String password);

    UserProfileDto getUserProfileById(String id);
}

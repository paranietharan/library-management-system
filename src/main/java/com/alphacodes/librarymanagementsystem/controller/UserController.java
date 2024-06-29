package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.*;
import com.alphacodes.librarymanagementsystem.Model.Student;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.OTPservice.OTPServiceImpl;
import com.alphacodes.librarymanagementsystem.repository.StudentRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public UserController(UserService userService, UserRepository userRepository, StudentRepository studentRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

   @PostMapping("/saveUser")
        public ResponseEntity<UserSaveResponse> postDetails(@RequestBody UserSaveRequest userSaveRequest) {
       Student student = studentRepository.findByIndexNumber(userSaveRequest.getIndexNumber());
       if (student != null
               && student.getEmailAddress().equals(userSaveRequest.getEmailAddress())
               && student.getPhoneNumber().equals(userSaveRequest.getPhoneNumber())) {

           System.out.println("Student: " + student.getFirstName());
           return new  ResponseEntity<>( userService.saveDetails(userSaveRequest, student), HttpStatus.CREATED);
       }
       System.out.println("Student not found");
        return null;
   }

    @GetMapping("/getAllUser")
        public List<User> getAllUserDetails() {
    return userService.getAllUserDetails();
    }

    @PostMapping("/login")
       public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Received request to login user: " + loginRequest.getEmailAddress() + " " + loginRequest.getPassword());
        return userService.performLogin(loginRequest);
   }
    @PostMapping("/forgotPassword")
        public Boolean forgotPassword(@RequestBody String email) {
           User user = userRepository.findByEmailAddress(email);
           if (user == null) {
               return false;
           }
           return userService.forgotPassword(email);
    }

    @PostMapping("/changePassword")
        public Boolean changePassword(@RequestBody String email, String password) {
            return userService.changePassword(email, password);
    }

    @PostMapping("/verifyOTP")
        public Boolean verifyOTP(@RequestBody String email, String otp) {
            User user = userRepository.findByEmailAddress(email);
            OTPServiceImpl otpService = new OTPServiceImpl();
            return otpService.verifyOTP(user.getEmailAddress(), otp);
    }

    // Get user profile
    @GetMapping("/getUserProfile/{id}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable String id) {
        System.out.println("Received request to get profile for userID: " + id);
        UserProfileDto userProfileDto = userService.getUserProfileById(id);

        // If user profile is not found return 404
        if (userProfileDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(userProfileDto);
    }
}

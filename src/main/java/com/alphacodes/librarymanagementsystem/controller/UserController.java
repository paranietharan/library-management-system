package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.LoginResponse;
import com.alphacodes.librarymanagementsystem.DTO.UserSaveRequest;
import com.alphacodes.librarymanagementsystem.DTO.UserSaveResponse;
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
              return new  ResponseEntity<>( userService.saveDetails(userSaveRequest, student), HttpStatus.CREATED);
       }
        return null;
   }

    @GetMapping("/getAllUser")
        public List<User> getAllUserDetails() {
    return userService.getAllUserDetails();
    }

    @PostMapping("/login")
       public ResponseEntity<LoginResponse> login(@RequestBody String email, String password) {
        return userService.performLogin(email, password);
   }
    @PostMapping("/forgotPassword")
        public Boolean forgotPassword(@RequestBody String email) {
           User user = userRepository.findByEmailAddress(email);
           if (user == null) {
               return false;
           }
           userService.forgotPassword(email);
           return true;
    }

    @PostMapping("/changePassword")
        public ResponseEntity<String> changePassword(@RequestBody String email, String password) {
            User user1 = userRepository.findByEmailAddress(email);
              if (user1 == null) {
                return ResponseEntity.ok("User not found");
              }
            userService.changePassword(email, password);
            return ResponseEntity.ok("Password changed successfully");
    }

    @PostMapping("/verifyOTP")
        public ResponseEntity<String> verifyOTP(@RequestBody String email, String otp) {
           User user = userRepository.findByEmailAddress(email);
        OTPServiceImpl otpService = new OTPServiceImpl();
           if(otpService.verifyOTP(user.getEmailAddress(), otp))
           {
               return ResponseEntity.ok("OTP verified successfully");
           }
           return ResponseEntity.ok("OTP not verified");
    }

}

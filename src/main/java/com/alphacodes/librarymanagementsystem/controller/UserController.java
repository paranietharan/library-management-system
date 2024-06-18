package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.LoginRequest;
import com.alphacodes.librarymanagementsystem.DTO.LoginResponse;
import com.alphacodes.librarymanagementsystem.DTO.UserDto;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.OTPservice.OTPServiceImpl;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

   @PostMapping("/saveUser")
        public User postDetails(@RequestBody UserDto userDto) {
    return userService.saveDetails(userDto);
   }

    @GetMapping("/getAllUser")
        public List<User> getAllUserDetails() {
    return userService.getAllUserDetails();
    }

    @PostMapping("/login")
       public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
           String userEmailAddress = loginRequest.getEmail();
           String password = loginRequest.getPassword();
           LoginResponse loginResponse = userService.performlogin(userEmailAddress, password);
           return ResponseEntity.ok(loginResponse);

   }
    @PostMapping("/forgotPassword")
        public ResponseEntity<String> forgotPassword(@RequestBody String email) {
           User user = userRepository.findByEmailAddress(email);
           if (user == null) {
               return ResponseEntity.ok("User not found");
           }
           forgotPassword(email);
           return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/changePassword")
        public ResponseEntity<String> changePassword(@RequestBody String email, String password) {
              User user1 = userRepository.findByEmailAddress(email);
              if (user1 == null) {
                return ResponseEntity.ok("User not found");
              }
              changePassword(email, password);
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

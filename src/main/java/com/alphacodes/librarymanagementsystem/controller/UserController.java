package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.*;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.OTPservice.OTPServiceImpl;
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


    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/checkDetails")
        public UserCheckResponse checkDetails(@RequestBody UserSaveRequest userSaveRequest) {
        User user = userRepository.findByEmailAddress(userSaveRequest.getEmailAddress());
        if (user == null) {
            return userService.checkDetails(userSaveRequest);
        }
        return new UserCheckResponse(null, null, null, null, "User already exists");
    }

    @PostMapping("/sendOtp")
        public Boolean sendOtp(@RequestBody EmailDto emailDto) {
        return userService.sendOtp(emailDto.getEmailAddress());
    }

   @PostMapping("/saveUser")
        public ResponseEntity<Boolean> postDetails(@RequestBody UserSaveAndVerifyOtpDto userSaveAndVerifyOtpDto) {
        User user = mapToUser(userSaveAndVerifyOtpDto);
        VerifyOtpDto verifyOtpDto = new VerifyOtpDto();
        verifyOtpDto.setEmailAddress(userSaveAndVerifyOtpDto.getEmailAddress());
        verifyOtpDto.setOtpValue(userSaveAndVerifyOtpDto.getOtpValue());
        System.out.println("Received request to save user: " + userSaveAndVerifyOtpDto.getEmailAddress() + " " + userSaveAndVerifyOtpDto.getOtpValue());
        OTPServiceImpl otpService = new OTPServiceImpl();
        if (otpService.verifyOTP(verifyOtpDto)) {
            return ResponseEntity.ok(userService.saveDetails(user));
        }
        return null;
   }

    @GetMapping("/getAllUser")
        public List<User> getAllUserDetails() {
    return userService.getAllUserDetails();
    }

    @PostMapping("/login")
       public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Received request to login user: " + loginRequest.getEmailAddress() + " " + loginRequest.getPassword());
        return userService.performLogin(loginRequest);
   }
    @PostMapping("/forgotPassword")
        public Boolean forgotPassword(@RequestBody EmailDto emailDto) {
           User user = userRepository.findByEmailAddress(emailDto.getEmailAddress());
           if (user == null) {
               System.out.println("User not found");
               return false;
           }
           System.out.println("Received request to send OTP to email: " + emailDto.getEmailAddress());
           return userService.sendOtp(emailDto.getEmailAddress());
    }

    @PostMapping("/changePassword")
        public Boolean changePassword(@RequestBody LoginRequest loginRequest) {
            return userService.changePassword(loginRequest.getEmailAddress(), loginRequest.getPassword());
    }

    @PostMapping("/verifyOTP")
        public Boolean verifyOTP(@RequestBody VerifyOtpDto verifyOtpDto) {
        OTPServiceImpl otpService = new OTPServiceImpl();
        return otpService.verifyOTP(verifyOtpDto);
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

    public User mapToUser(UserSaveAndVerifyOtpDto userSaveAndVerifyOtpDto) {
        User user = new User();
        user.setFirstName(userSaveAndVerifyOtpDto.getFirstName());
        user.setLastName(userSaveAndVerifyOtpDto.getLastName());
        user.setUserID(userSaveAndVerifyOtpDto.getIndexNumber());
        user.setEmailAddress(userSaveAndVerifyOtpDto.getEmailAddress());
        user.setPhoneNumber(userSaveAndVerifyOtpDto.getPhoneNumber());
        user.setPassword(userSaveAndVerifyOtpDto.getPassword());
        return user;
    }
}

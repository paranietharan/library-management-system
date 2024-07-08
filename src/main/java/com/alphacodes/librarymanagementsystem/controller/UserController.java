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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
            return new ResponseEntity<>(userService.saveDetails(userSaveRequest, student), HttpStatus.CREATED);
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

    //TODO: function to update user profile - fristname, lastname, email, phone number, profile image
    @PutMapping("/updateUserProfile/{id}")
    public ResponseEntity<UserDto> updateUserProfile(
            @PathVariable String id,

            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String Email,
            @RequestParam String phoneNumber,
            @RequestParam(required = false) MultipartFile profileImg
    ) throws IOException {
        System.out.println("Received request to update profile for userID: " + id);

        // Create a new user profile DTO
        UserDto userDto = new UserDto();
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(Email);
        userDto.setPhoneNumber(phoneNumber);

        if(profileImg != null) {
            userDto.setProfileImg(profileImg.getBytes());
        } else {
            userDto.setProfileImg(null);
        }

        UserDto updatedUserDto = userService.updateUserProfile(id, userDto);

        // If user profile is not found return 404
        if (updatedUserDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(updatedUserDto);
    }

    @GetMapping("/getUserProfileDetails/{id}")
    public ResponseEntity<UserDto> getUserProfileDetails(@PathVariable String id) {
        UserDto userDto = userService.getUserProfileDetails(id);
        return ResponseEntity.ok(userDto);
    }

}

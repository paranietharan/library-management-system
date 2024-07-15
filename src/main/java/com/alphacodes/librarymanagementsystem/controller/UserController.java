package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.*;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.OTPservice.OTPServiceImpl;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserController(UserService userService, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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


       // make some changes here
    @PostMapping("/resetPassword")
        public Boolean changePassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
            if (resetPasswordRequest.getIsVerified()){
                return userService.changePassword(resetPasswordRequest.getEmailAddress(), resetPasswordRequest.getPassword());
            }
            return false;
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

    @PutMapping("/updateUserProfile/{id}")
    public ResponseEntity<UserDto> updateUserProfile(
            @PathVariable String id,

            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String Email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam(value = "profileImg", required = false) MultipartFile profileImg
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

    @PostMapping("/changePassword/{id}")
    public Boolean changePassword(@PathVariable String id, @RequestBody ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByUserID(id);
        boolean isPasswordMatch = bCryptPasswordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword());
        if (isPasswordMatch) {
            userService.changePassword(user.getEmailAddress(), changePasswordRequest.getNewPassword());
            return true;
        }
        return false;
    }

    @DeleteMapping("/deleteUserProfile/{id}")
    public Boolean deleteUserProfile(@PathVariable String id) {
        userService.deleteUserProfile(id);
        return true;
    }

    @PostMapping("librarian/addUser")
    public ResponseEntity<String> addUser(
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("indexNumber") String indexNumber
    ) {
        return new ResponseEntity<>(userService
                .LibrarianAddUser(email, phoneNumber, indexNumber), HttpStatus.OK
        );
    }
}

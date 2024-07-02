package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.*;
//import com.alphacodes.librarymanagementsystem.EmailService.EmailServiceImpl;
import com.alphacodes.librarymanagementsystem.JwtAuthenticationConfig.JWTauthentication;
import com.alphacodes.librarymanagementsystem.Model.Student;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.OTPservice.OTPServiceImpl;
import com.alphacodes.librarymanagementsystem.enums.Role;
import com.alphacodes.librarymanagementsystem.repository.StudentRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTauthentication jwtA;


    public UserServiceImpl(UserRepository userRepository ,StudentRepository studentRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JWTauthentication jwtA) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtA = jwtA;
    }


    @Override
    public ResponseEntity<LoginResponse> performLogin(LoginRequest loginRequest) {
        User user = userRepository.findByEmailAddress(loginRequest.getEmailAddress());
        System.out.println("email" + loginRequest.getEmailAddress());
        if (user == null) {
            return new ResponseEntity<>(new LoginResponse("User not found", false, null), HttpStatus.NOT_FOUND);
        }
        System.out.println("User: " + user.getFirstName());
        System.out.println("Password: " + loginRequest.getPassword());
        boolean isPasswordMatched = bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (isPasswordMatched) {
            String token = jwtA.generateToken(user);
            return new ResponseEntity<>(new LoginResponse("Login Successful", true, token), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new LoginResponse("Incorrect Password", false, null), HttpStatus.UNAUTHORIZED);
        }
    }
    
    @Override
    public UserCheckResponse checkDetails(UserSaveRequest userSaveRequest) {
        Student student = studentRepository.findByIndexNumber(userSaveRequest.getIndexNumber());
        if (student != null
                && student.getEmailAddress().equals(userSaveRequest.getEmailAddress())
                && student.getPhoneNumber().equals(userSaveRequest.getPhoneNumber())) {

            System.out.println("Student: " + student.getFirstName());
            return mapToUserCheckResponse(student);
        }
        System.out.println("Student not found");
        System.out.println("Student: " + userSaveRequest.getFirstName());
        return null;
    }


   @Override
    public Boolean saveDetails(User user) {
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole(Role.MEMBER);
        System.out.println("User: " + user.getFirstName());
        userRepository.save(user);
        return true;
    }
    //TODO: return type

    @Override
    public List<User> getAllUserDetails() {
        return userRepository.findAll();
    }

    @Override
    public Boolean sendOtp(String email) {
        System.out.println("Email: " + email);
        OTPServiceImpl otpService = new OTPServiceImpl();
        //EmailServiceImpl emailService = new EmailServiceImpl();
        String otp = otpService.generateOTP(email);
        //emailService.sendOTP(email, otp);
        System.out.println("OTP: " + otp);
        return true;
    }

    @Override
    public Boolean changePassword(String email, String password) {
        User user = userRepository.findByEmailAddress(email);
        if (user == null) {
            return false;
        }
        System.out.println("email: " + email);
        System.out.println("Password: " + password);
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserProfileDto getUserProfileById(String id) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUserID(id));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return mapToUserProfileDto(user);
        } else {
            return null;
        }
    }



    public UserCheckResponse mapToUserCheckResponse(Student student) {
        UserCheckResponse userCheckResponse = new UserCheckResponse();
        userCheckResponse.setFirstName(student.getFirstName());
        userCheckResponse.setLastName(student.getLastName());
        userCheckResponse.setDateOfBirth(student.getDateOfBirth());
        userCheckResponse.setGrade(student.getGrade());
        userCheckResponse.setIsUserExists(false);
        return userCheckResponse;
    }

    private UserProfileDto mapToUserProfileDto(User user) {
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setFirstName(user.getFirstName());
        userProfileDto.setLastName(user.getLastName());
        userProfileDto.setRole(user.getRole());
        userProfileDto.setProfileImg(user.getProfileImg());
        userProfileDto.setUserID(user.getUserID());
        return userProfileDto;
    }

}
package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.LoginResponse;
import com.alphacodes.librarymanagementsystem.DTO.UserProfileDto;
import com.alphacodes.librarymanagementsystem.DTO.UserSaveRequest;
import com.alphacodes.librarymanagementsystem.DTO.UserSaveResponse;
import com.alphacodes.librarymanagementsystem.EmailService.EmailServiceImpl;
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
    public ResponseEntity<LoginResponse> performLogin(String userEmailAddress, String password) {
        User user = userRepository.findByEmailAddress(userEmailAddress);
        if (user == null) {
            return new ResponseEntity<>(new LoginResponse("User not found", false, null), HttpStatus.NOT_FOUND);
        }
        boolean isPasswordMatched = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (isPasswordMatched) {
            String token = jwtA.generateToken(user);
            return new ResponseEntity<>(new LoginResponse("Login Successful", true, token), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new LoginResponse("Incorrect Password", false, null), HttpStatus.UNAUTHORIZED);
        }
    }

   @Override
    public UserSaveResponse saveDetails(UserSaveRequest userSaveRequest, Student student) {
        User user = mapToUser(userSaveRequest);
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole(Role.MEMBER);
        userRepository.save(user);
        return mapToUserSaveResponse(student);
    }
    //TODO: return type

    @Override
    public List<User> getAllUserDetails() {
        return userRepository.findAll();
    }

    @Override
    public boolean forgotPassword(String email) {
        OTPServiceImpl otpService = new OTPServiceImpl();
        EmailServiceImpl emailService = new EmailServiceImpl();
        String otp = otpService.generateOTP(email);
        emailService.sendOTP(email, otp);
        return true;
    }

    @Override
    public boolean changePassword(String email, String password) {
        User user = userRepository.findByEmailAddress(email);
        if (user == null) {
            return false;
        }
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

    public User mapToUser(UserSaveRequest userSaveRequest) {
        User user = new User();
        user.setFirstName(userSaveRequest.getFirstName());
        user.setLastName(userSaveRequest.getLastName());
        user.setUserID(userSaveRequest.getIndexNumber());
        user.setEmailAddress(userSaveRequest.getEmailAddress());
        user.setPhoneNumber(userSaveRequest.getPhoneNumber());
        user.setPassword(userSaveRequest.getPassword());
        return user;
    }

    public UserSaveResponse mapToUserSaveResponse(Student student) {
        UserSaveResponse userSaveResponse = new UserSaveResponse();
        userSaveResponse.setFirstName(student.getFirstName());
        userSaveResponse.setLastName(student.getLastName());
        userSaveResponse.setDateOfBirth(student.getDateOfBirth());
        userSaveResponse.setGrade(student.getGrade());
        return userSaveResponse;
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
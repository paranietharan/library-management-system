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
import com.alphacodes.librarymanagementsystem.util.PasswordUtil;
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
    public LoginResponse performLogin(LoginRequest loginRequest) {
        User user = userRepository.findByEmailAddress(loginRequest.getEmailAddress());
        System.out.println("email" + loginRequest.getEmailAddress());
        if (user == null) {
            return new LoginResponse("User not found", false, null);
        }
        System.out.println("User: " + user.getFirstName());
        System.out.println("Password: " + loginRequest.getPassword());
        boolean isPasswordMatched = bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (isPasswordMatched) {
            String token = jwtA.generateToken(user);
            return new LoginResponse("Login Successful", true, token);
        } else {
            return new LoginResponse("Incorrect Password", false, null);
        }
    }
    
    @Override
    public UserCheckResponse checkDetails(UserSaveRequest userSaveRequest) {
        Student student = studentRepository.findByIndexNumber(userSaveRequest.getIndexNumber());

        // TODO: Delete dev code
//        if(student != null){
//            System.out.println("Student found with : " + student.getFirstName());
//            System.out.println("check email " +
//                    student.getEmailAddress().equals(userSaveRequest.getEmailAddress())
//            );
//            System.out.println("check phone " +
//                    student.getPhoneNumber().equals(userSaveRequest.getPhoneNumber())
//            );
//        }

        if (student != null
                && student.getEmailAddress().equals(userSaveRequest.getEmailAddress())
                && student.getPhoneNumber().equals(userSaveRequest.getPhoneNumber())) {

            System.out.println("Student found with : " + student.getFirstName());
            return mapToUserCheckResponse(student);
        }

        System.out.println("Student account not created: " + userSaveRequest.getFirstName());
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
        userCheckResponse.setMessage("User found");
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

    @Override
    public UserDto updateUserProfile(String id, UserDto userDto) {
        User user = userRepository.findByUserID(id);

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmailAddress(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());

        // Update profile image if provided, otherwise keep existing
        if (userDto.getProfileImg() != null) {
            user.setProfileImg(userDto.getProfileImg());
        }

        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }

    @Override
    public UserDto getUserProfileDetails(String id) {
        User user = userRepository.findByUserID(id);
        return convertToDto(user);
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserID(user.getUserID());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmailAddress());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setProfileImg(user.getProfileImg());
        // Set other fields as needed
        return dto;
    }

    @Override
    public Boolean deleteUserProfile(String id) {
        User user = userRepository.findByUserID(id);
        if (user == null) {
            return false;
        }
        userRepository.delete(user);
        return true;
    }

    @Override
    public String LibrarianAddUser(String email, String phoneNumber, String indexNumber) {
        // check the above details user is member or not
        User user1 = userRepository.findByUserID(indexNumber);
        if(user1 != null){
            return "User already exists";
        }

        // check if student exists
        Student student = studentRepository.findByIndexNumber(indexNumber);

        String password = PasswordUtil.generateStrongPassword();
        if (student != null) {
            User user = new User();
            user.setFirstName(student.getFirstName());
            user.setLastName(student.getLastName());
            user.setEmailAddress(email);
            user.setPhoneNumber(phoneNumber);
            user.setUserID(indexNumber);
            String encryptedPassword2 = bCryptPasswordEncoder.encode(password);
            user.setPassword(encryptedPassword2);
            user.setRole(Role.MEMBER);
            userRepository.save(user);

            System.out.println("User added successfully"
                    + "\npassword: " + password
                    + "\nEmail Address: " + email
                    + "\nPhone Number: " + phoneNumber
                    + "\nIndex Number: " + indexNumber);
            return "User added successfully";
        } else {
            return "User not found in Student Database";
        }
    }

    @Override
    public void createAdminAccount(){
        User user = userRepository.findByUserID("admin");
        if(user != null){
            return;
        }
        User admin = new User();
        admin.setUserID("admin");
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setEmailAddress("admin@admin.com");
        admin.setPhoneNumber("0000000000");
        String encryptedPassword = bCryptPasswordEncoder.encode("admin");
        admin.setPassword(encryptedPassword);
        admin.setRole(Role.LIBRARIAN);
        userRepository.save(admin);
    }

}
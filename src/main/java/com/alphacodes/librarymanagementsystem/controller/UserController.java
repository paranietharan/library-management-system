package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.LoginRequest;
import com.alphacodes.librarymanagementsystem.DTO.LoginResponse;
import com.alphacodes.librarymanagementsystem.DTO.UserDto;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

   @PostMapping("/saveUser")
        public User postDetails(@RequestBody UserDto userDto) {
    return userService.saveDetails(userDto);
   }

   // Save new user with all  details
    @PostMapping("createNewUser")
    public User createNewUser(@RequestBody User user) {
        return userService.createNewUser(user);
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

}

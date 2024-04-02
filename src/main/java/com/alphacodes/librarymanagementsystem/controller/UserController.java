package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.LoginRequest;
import com.alphacodes.librarymanagementsystem.DTO.LoginResponse;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
   @Autowired
    private UserService userService;
   @PostMapping("/saveUser")
    public User postDetails(@RequestBody User user) {
    return userService.saveDetails(user);
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
    return ResponseEntity.ok(loginResponse)
   ;

   }

}

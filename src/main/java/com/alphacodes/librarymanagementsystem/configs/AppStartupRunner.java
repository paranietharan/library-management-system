package com.alphacodes.librarymanagementsystem.configs;

import com.alphacodes.librarymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppStartupRunner {

    @Autowired
    private UserService userService;

    @Bean
    public CommandLineRunner run() {
        return args -> {
            userService.createAdminAccount();
        };
    }
}


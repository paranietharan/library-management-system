package com.alphacodes.librarymanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

// scheduled task execution capability. when @scheduled annotation is used in a method,
// it will be executed at the specified time.
@EnableScheduling
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.alphacodes.librarymanagementsystem.repository")
public class LibraryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemApplication.class, args);
	}

}

package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping("student/")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/upload-students")
    public ResponseEntity<String> uploadStudents(@RequestBody MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please upload a CSV file!", HttpStatus.BAD_REQUEST);
        }

        try {
            studentService.saveStudentsFromCsv(file);
            return new ResponseEntity<>("Students saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing the file.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.alphacodes.librarymanagementsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface StudentService {
    void saveStudentsFromCsv(MultipartFile file) throws IOException;
}

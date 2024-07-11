package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.Model.Student;
import com.alphacodes.librarymanagementsystem.repository.StudentRepository;
import com.alphacodes.librarymanagementsystem.service.StudentService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public void saveStudentsFromCsv(MultipartFile file) throws IOException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> rows = reader.readAll();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (String[] row : rows) {
                String indexNumber = row[0];
                Student existingStudent = studentRepository.findByIndexNumber(indexNumber);
                if (existingStudent == null) {
                    Student student = new Student();
                    student.setIndexNumber(indexNumber);
                    student.setFirstName(row[1]);
                    student.setLastName(row[2]);
                    student.setEmailAddress(row[3]);
                    student.setPhoneNumber(row[4]);
                    student.setGrade(row[5]);
                    Date dateOfBirth = dateFormat.parse(row[6]);
                    student.setDateOfBirth(dateOfBirth);
                    studentRepository.save(student);
                } else{
                    System.out.println("Student with index number "
                            + indexNumber
                            + " Name : "
                            + existingStudent.getFirstName()
                            + " already exists.");
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}

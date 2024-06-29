package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    //@Nullable
    Student findByIndexNumber(String indexNumber);

}

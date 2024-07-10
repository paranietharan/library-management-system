package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    //@Nullable
    // custom query to get student by index number
    @Query("SELECT s FROM Student s WHERE s.indexNumber = ?1")
    Student findByIndexNumber(String indexNumber);

}

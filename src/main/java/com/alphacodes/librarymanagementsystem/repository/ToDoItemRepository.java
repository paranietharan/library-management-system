package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long> {

    //  custom query to get all to do items by user id
    @Query("SELECT t FROM ToDoItem t WHERE t.user.userID = ?1")
    List<ToDoItem> findByUserId(String userId);
}

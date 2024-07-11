package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    // custom query to find notes by user id
    @Query("SELECT n FROM Note n WHERE n.user.userID = ?1")
    List<Note> findByUserId(String userId);
}

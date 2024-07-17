package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.Resource;
import com.alphacodes.librarymanagementsystem.Model.ResourceComment;
import com.alphacodes.librarymanagementsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceCommentRepository extends JpaRepository<ResourceComment, Long>{
List<ResourceComment> findByBook(Resource book);

    //method to find all comments by a user
    @Query("SELECT c FROM ResourceComment c WHERE c.member = ?1")
    Iterable<ResourceComment> findByUser(User user);


    //method to find all comments by a resource
    @Query("SELECT c FROM ResourceComment c WHERE c.book = ?1")
    Iterable<ResourceComment> findByResource(Resource resource);
}

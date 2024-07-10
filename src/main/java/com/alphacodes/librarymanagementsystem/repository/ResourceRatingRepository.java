package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.Resource;
import com.alphacodes.librarymanagementsystem.Model.ResourceComment;
import com.alphacodes.librarymanagementsystem.Model.ResourceRating;
import com.alphacodes.librarymanagementsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRatingRepository extends JpaRepository<ResourceRating, Long>{
    List<ResourceRating> findByBook(Resource book);

    @Query("SELECT rr FROM ResourceRating rr WHERE rr.book = :book AND rr.member = :member")
    Optional<ResourceRating> findByResourceAndUser(@Param("book") Resource book, @Param("member") User member);
}

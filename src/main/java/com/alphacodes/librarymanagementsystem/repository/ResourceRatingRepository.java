package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.Resource;
import com.alphacodes.librarymanagementsystem.Model.ResourceComment;
import com.alphacodes.librarymanagementsystem.Model.ResourceRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRatingRepository extends JpaRepository<ResourceRating, Long>{
    List<ResourceRating> findByBook(Resource book);
}

package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long>{
    List<Resource> findByCategory(String category);
    List<Resource> findByTitle(String title);

    @Query("SELECT r FROM Resource r WHERE r.title LIKE %:keyword%")
    List<Resource> findByTitleMatchKeyword(@Param("keyword") String keyword);

    @Query("SELECT r FROM Resource r WHERE r.author LIKE %:author%")
    List<Resource> findByKeywordRelatedAuthors(@Param("author") String author);

    Resource findByIsbn(String isbn);
}

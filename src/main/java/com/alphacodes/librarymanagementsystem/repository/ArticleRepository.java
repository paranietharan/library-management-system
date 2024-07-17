package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.Article;
import com.alphacodes.librarymanagementsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findByAuthor_UserID(String userId);

    // search article that titles mathces the words in the heading
    @Query("SELECT a FROM Article a WHERE a.title LIKE %:heading%")
    List<Article> findByTitleContaining(@Param("heading") String heading);

    // search article that body mathces the words in the body
    @Query("SELECT a FROM Article a WHERE a.body LIKE %:body%")
    List<Article> findByBodyContaining(String body);

    // search article by author
    @Query("SELECT a FROM Article a WHERE a.author = :author")
    Iterable<Article> findByAuthor(@Param("author") User author);
}

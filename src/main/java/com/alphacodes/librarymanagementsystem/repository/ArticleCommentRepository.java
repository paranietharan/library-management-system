package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.Article;
import com.alphacodes.librarymanagementsystem.Model.ArticleComment;
import com.alphacodes.librarymanagementsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Integer> {

    List<ArticleComment> findByArticle(Article article);

    // Find all comments by a user
    @Query("SELECT ac FROM ArticleComment ac WHERE ac.commenter = :user")
    Iterable<ArticleComment> findByUser(User user);
}

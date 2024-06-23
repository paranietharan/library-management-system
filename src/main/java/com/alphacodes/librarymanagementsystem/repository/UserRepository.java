package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    User findByEmailAddress(String email_address);

    @Query(value = "SELECT * FROM users WHERE user_id = :userId", nativeQuery = true)
    User findByUserID(int userId);

}

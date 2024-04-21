package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    User findByEmailAddress(String userEmailAddress);
    User findByUserID(int uID);

}

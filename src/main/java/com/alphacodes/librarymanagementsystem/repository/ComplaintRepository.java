package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long>{

    @Query("SELECT c FROM Complaint c WHERE c.member.userID = :userId")
    Optional<List<Complaint>> findByMember_UserID(@Param("userId") String userId);

    // Find all complaints that are resolved using custom query
    @Query("SELECT c FROM Complaint c WHERE c.isResolved = :b")
    List<Complaint> findByResolved(boolean b);
}

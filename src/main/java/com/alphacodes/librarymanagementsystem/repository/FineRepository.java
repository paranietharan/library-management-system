package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long>{
    List<Fine> findByPaidStatus(boolean b);

    // Custom query to find fine by member ID and issue ID
    @Query("SELECT f FROM Fine f WHERE f.member.userID = :memberId AND f.issue.issueId = :issueId")
    Fine findByUserIdAndIssueId(String memberId, Long issueId);

    // Custom query to find fine by member ID
    @Query("SELECT f FROM Fine f WHERE f.member.userID = :memberId AND f.paidStatus = false")
    Fine findByUserID(String memberId);

    // Custom query to find all fines by member ID
    @Query("SELECT f FROM Fine f WHERE f.member.userID = :memberId")
    List<Fine> findAllFinesByMemberID(String memberId);
}

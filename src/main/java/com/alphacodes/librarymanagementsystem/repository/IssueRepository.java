package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.Issue;
import com.alphacodes.librarymanagementsystem.Model.Resource;
import com.alphacodes.librarymanagementsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long>{

    // Custom query to find issue by member ID
    @Query("SELECT i FROM Issue i WHERE i.member.userID = :userId")
    Optional<Issue> findIssueByMemberId(@Param("userId") int userId);

    // Custom query to find issue by member ID and resource ID
    @Query("SELECT i FROM Issue i WHERE i.member.userID = :userId AND i.book.id = :resourceId AND i.returned = false")
    Optional<Issue> findNonReturnIssueByUserIdAndResourceId(@Param("userId") String userId, @Param("resourceId") Long resourceId);

    // Custom query to find issue by member ID
    @Query("SELECT i FROM Issue i WHERE i.member.userID = :memberId AND i.returned = false")
    Optional<Issue> findNonReturnIssueByUserId(String memberId);

    // Custom query to find issue by member ID
    @Query("SELECT i FROM Issue i WHERE i.member.userID = :memberId AND i.returned = true")
    List<Issue> findReturnIssueByUserId(String memberId);

    // Custom query to find issue by return type
    @Query("SELECT i FROM Issue i WHERE i.returned = :b")
    List<Issue> findAllByReturned(boolean b);

    // Custom query to find issue by user
    @Query("SELECT i FROM Issue i WHERE i.member = :user")
    Iterable<Issue> findByUser(User user);

    // Custom query to find issue by resource
    @Query("SELECT i FROM Issue i WHERE i.book = :resource")
    Iterable<Issue> findByResource(Resource resource);
}

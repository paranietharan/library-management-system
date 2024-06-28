package com.alphacodes.librarymanagementsystem.service;

public interface IssueService {
    String issueResource(Long resourceId, int memberId, int librarianId);
    String returnResource(Long resourceId, int memberId);
}

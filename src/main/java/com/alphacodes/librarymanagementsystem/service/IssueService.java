package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.IssueDto;

import java.util.List;

public interface IssueService {
    String issueResource(Long resourceId, String memberId);
    String returnResource(Long resourceId, String memberId);
    List<IssueDto> getIssueHistory(String memberId);
}

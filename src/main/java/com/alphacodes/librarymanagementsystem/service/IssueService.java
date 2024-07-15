package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.IssueDto;

import java.util.List;

public interface IssueService {
    String issueResource(Long resourceId, String memberId);
    String returnResource(String memberId);

    IssueDto checkResource(String memberId);

    List<IssueDto> getHistory(String memberId);

    int getAllIssuedResourcesCount();
}

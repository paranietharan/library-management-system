package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/issues")
public class IssueController {
    @Autowired
    private IssueService issueService;

    // Issues books
    @PostMapping("/issue")
    public String issueResource(@RequestParam Long resourceId, @RequestParam int memberId, @RequestParam int librarianId) {
        return issueService.issueResource(resourceId, memberId, librarianId);
    }
}

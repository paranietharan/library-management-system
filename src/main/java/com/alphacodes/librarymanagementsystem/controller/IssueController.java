package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.IssueDto;
import com.alphacodes.librarymanagementsystem.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {
    @Autowired
    private IssueService issueService;

    // Issues books
    @PostMapping("/issue")
    public ResponseEntity<String> issueResource(@RequestParam Long resourceId, @RequestParam String memberId) {
        return ResponseEntity.ok(issueService.issueResource(resourceId, memberId));
    }

    // Get return Issued books
    @PostMapping("/return")
    public ResponseEntity<String> returnResource(@RequestParam Long resourceId, @RequestParam String memberId) {
        return ResponseEntity.ok(issueService.returnResource(resourceId, memberId));
    }

    // Get Issue History
    @GetMapping("/history/{memberId}")
    public ResponseEntity<List<IssueDto>> getIssueHistory(@PathVariable String memberId) {
        return ResponseEntity.ok(issueService.getIssueHistory(memberId));
    }
}

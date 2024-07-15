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
    public ResponseEntity<String> returnResource(@RequestParam String memberId) {
        return ResponseEntity.ok(issueService.returnResource(memberId));
    }

    // check a user has a book or not
    @GetMapping("/check")
    public ResponseEntity<IssueDto> checkResource(@RequestParam String memberId) {
        return ResponseEntity.ok(issueService.checkResource(memberId));
    }

    // get non returned books by user
    @GetMapping("/history/{memberId}")
    public ResponseEntity<List<IssueDto>> getHistory(@PathVariable String memberId) {
        return ResponseEntity.ok(issueService.getHistory(memberId));
    }

    @GetMapping("/issue-book-count")
    public ResponseEntity<Integer> getIssuedBookCount() {
        return ResponseEntity.ok(issueService.getAllIssuedResourcesCount());
    }
}

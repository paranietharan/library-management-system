package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.ComplaintRequest;
import com.alphacodes.librarymanagementsystem.DTO.ComplaintResponse;
import com.alphacodes.librarymanagementsystem.service.ComplaintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/{userID}/complaint")
public class ComplaintController {
    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping
    public ResponseEntity<ComplaintResponse> addComplaint(@PathVariable int userID, @RequestBody ComplaintRequest complaintRequest) {
        ComplaintResponse complaintResponse = complaintService.addComplaint(userID, complaintRequest);
        return ResponseEntity.ok(complaintResponse);
    }

    @GetMapping
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
        List<ComplaintResponse> complaintResponses = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaintResponses);
    }

    @GetMapping("/{cID}")
    public ResponseEntity<ComplaintResponse> getComplaintById(@PathVariable Long cID) {
        ComplaintResponse complaintResponse = complaintService.getComplaint(cID);
        return ResponseEntity.ok(complaintResponse);
    }

    @DeleteMapping("/{cID}")
    public ResponseEntity<String> deleteComplaint(@PathVariable Long cID) {
        String response = complaintService.deleteComplaint(cID);
        return ResponseEntity.ok(response);
    }
}

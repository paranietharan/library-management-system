package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.ComplaintDto;
import com.alphacodes.librarymanagementsystem.service.ComplaintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaint")
public class ComplaintController {
    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping
    public ResponseEntity<ComplaintDto> addComplaint(@RequestBody ComplaintDto complaintDto) {
        ComplaintDto complaintDto1 = complaintService.addComplaint(complaintDto);
        return ResponseEntity.ok(complaintDto1);
    }

    @GetMapping
    public ResponseEntity<List<ComplaintDto>> getAllComplaints() {
        List<ComplaintDto> complaintDto = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaintDto);
    }

    @GetMapping("/{cID}")
    public ResponseEntity<ComplaintDto> getComplaintById(@PathVariable Long cID) {
        ComplaintDto complaintDto = complaintService.getComplaint(cID);
        return ResponseEntity.ok(complaintDto);
    }

    @DeleteMapping("/{cID}")
    public ResponseEntity<String> deleteComplaint(@PathVariable Long cID) {
        String response = complaintService.deleteComplaint(cID);
        return ResponseEntity.ok(response);
    }
}

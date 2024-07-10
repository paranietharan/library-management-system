package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.ComplaintDto;
import com.alphacodes.librarymanagementsystem.DTO.ComplaintViewDto;
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

    @PostMapping("/new")
    public ResponseEntity<ComplaintDto> MakeNewComplaint(
            @RequestBody ComplaintDto complaintDto
    ) {
        ComplaintDto complaintDto1 = complaintService.addComplaint(complaintDto);
        return ResponseEntity.ok(complaintDto1);
    }

    @GetMapping("/{complaintId}")
    public ResponseEntity<ComplaintViewDto> GetComplainById(
            @PathVariable Long complaintId
    ) {
        ComplaintViewDto complaintDto = complaintService.getComplaintById(complaintId);
        return ResponseEntity.ok(complaintDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ComplaintViewDto>> getAllComplaints() {
        List<ComplaintViewDto> complaintDto = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaintDto);
    }

    @DeleteMapping("/{complaintId}")
    public ResponseEntity<String> deleteComplaint(@PathVariable Long complaintId) {
        String response = complaintService.deleteComplaint(complaintId);
        return ResponseEntity.ok(response);
    }

    // Get by user id
    @GetMapping("/get")
    public ResponseEntity<List<ComplaintViewDto>> getComplaintsByUserId(@RequestParam String userID) {
        List<ComplaintViewDto> complaintDto = complaintService.getComplaintsByUserId(userID);
        return ResponseEntity.ok(complaintDto);
    }

    @PutMapping("/update/{complaintId}")
    public ResponseEntity<ComplaintDto> updateComplaint(
            @PathVariable Long complaintId,
            @RequestBody ComplaintDto complaintDto
    ) {
        ComplaintDto updatedComplaint = complaintService.updateComplaint(complaintDto, complaintId);
        return ResponseEntity.ok(updatedComplaint);
    }

    @PutMapping("/resolve/{complaintId}")
    public ResponseEntity<ComplaintViewDto> resolveComplaint(
            @PathVariable Long complaintId
    ) {
        ComplaintViewDto updatedComplaint = complaintService.resolveComplaint(complaintId);
        return ResponseEntity.ok(updatedComplaint);
    }

    // for accidentally clicked need to mark unresolve
    @PutMapping("/unresolve/{complaintId}")
    public ResponseEntity<ComplaintViewDto> unresolveComplaint(
            @PathVariable Long complaintId
    ) {
        ComplaintViewDto updatedComplaint = complaintService.unresolveComplaint(complaintId);
        return ResponseEntity.ok(updatedComplaint);
    }

    // get all unresolved complaints
    @GetMapping("/unresolved")
    public ResponseEntity<List<ComplaintViewDto>> getUnresolvedComplaints() {
        List<ComplaintViewDto> complaintDto = complaintService.getUnresolvedComplaints();
        return ResponseEntity.ok(complaintDto);
    }

    // get all resolved complaints
    @GetMapping("/resolved")
    public ResponseEntity<List<ComplaintViewDto>> getResolvedComplaints() {
        List<ComplaintViewDto> complaintDto = complaintService.getResolvedComplaints();
        return ResponseEntity.ok(complaintDto);
    }
}

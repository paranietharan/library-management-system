package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.ComplaintRequest;
import com.alphacodes.librarymanagementsystem.DTO.ComplaintResponse;

import java.util.List;

public interface ComplaintService {
    ComplaintResponse addComplaint(int userID, ComplaintRequest complaintRequest);
    String deleteComplaint(Long cID);
    List<ComplaintResponse> getAllComplaints();
    ComplaintResponse getComplaint(Long cID);
}

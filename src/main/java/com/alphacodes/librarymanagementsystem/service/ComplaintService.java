package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.ComplaintDto;



import java.util.List;

public interface ComplaintService {
    ComplaintDto addComplaint(ComplaintDto complaintDto);
    String deleteComplaint(Long complaintId);
    List<ComplaintDto> getAllComplaints();
    ComplaintDto getComplaint(Long complaintId);
}

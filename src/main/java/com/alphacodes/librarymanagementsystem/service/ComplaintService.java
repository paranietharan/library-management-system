package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.ComplaintDto;
import com.alphacodes.librarymanagementsystem.DTO.ComplaintViewDto;


import java.util.List;

public interface ComplaintService {
    ComplaintDto addComplaint(ComplaintDto complaintDto);
    List<ComplaintViewDto> getAllComplaints();
    ComplaintViewDto getComplaintById(Long complaintId);
    String deleteComplaint(Long complaintId);

    List<ComplaintViewDto> getComplaintsByUserId(String userId);

    ComplaintDto updateComplaint(ComplaintDto complaintDto, Long complaintId);

    ComplaintViewDto resolveComplaint(Long complaintId);

    ComplaintViewDto unresolveComplaint(Long complaintId);

    List<ComplaintViewDto> getUnresolvedComplaints();

    List<ComplaintViewDto> getResolvedComplaints();
}

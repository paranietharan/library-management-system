package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.ComplaintRequest;
import com.alphacodes.librarymanagementsystem.DTO.ComplaintResponse;
import com.alphacodes.librarymanagementsystem.Model.Complaint;
import com.alphacodes.librarymanagementsystem.repository.ComplaintRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.ComplaintService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public ComplaintServiceImpl(ComplaintRepository complaintRepository, UserRepository userRepository) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ComplaintResponse addComplaint(int userID, ComplaintRequest complaintRequest) {
        Complaint complaint = mapToComplaint(complaintRequest);
        complaint.setMember(userRepository.findByUserID(userID).orElse(null));
        Complaint newComplaint = complaintRepository.save(complaint);
        return mapToComplaintResponse(newComplaint);
    }

    @Override
    public String deleteComplaint(Long cID) {
        complaintRepository.deleteById(cID);
        return "Complaint deleted Successfully";
    }

    @Override
    public List<ComplaintResponse> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();
        return complaints.stream().map(this::mapToComplaintResponse).collect(Collectors.toList());
    }

    @Override
    public ComplaintResponse getComplaint(Long cID) {
        Complaint complaint = complaintRepository.findById(cID).orElseThrow(
                () -> new RuntimeException("Complaint not found with id " + cID));
        return mapToComplaintResponse(complaint);
    }

    private ComplaintResponse mapToComplaintResponse(Complaint complaint) {
        ComplaintResponse complaintResponse = new ComplaintResponse();
        complaintResponse.setUserID(complaint.getMember().getUserID());
        complaintResponse.setComplaint(complaint.getComplaint());
        return complaintResponse;
    }

    private Complaint mapToComplaint(ComplaintRequest complaintRequest) {
        Complaint complaint = new Complaint();
        complaint.setComplaint(complaintRequest.getComplaint());
        return complaint;
    }
}

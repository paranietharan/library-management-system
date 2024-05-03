package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.ComplaintDto;
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
    public ComplaintDto addComplaint(ComplaintDto complaintDto) {
        Complaint complaint = mapToComplaint(complaintDto);
        Complaint newComplaint = complaintRepository.save(complaint);
        return mapToComplaintDto(newComplaint);
    }

    @Override
    public String deleteComplaint(Long cID) {
        complaintRepository.deleteById(cID);
        return "Complaint deleted Successfully";
    }

    @Override
    public List<ComplaintDto> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();
        return complaints.stream().map(this::mapToComplaintDto).collect(Collectors.toList());
    }

    @Override
    public ComplaintDto getComplaint(Long cID) {
        Complaint complaint = complaintRepository.findById(cID).orElseThrow(
                () -> new RuntimeException("Complaint not found with id " + cID));
        return mapToComplaintDto(complaint);
    }

    private ComplaintDto mapToComplaintDto(Complaint complaint) {
        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setUserID(complaint.getMember().getUserID());
        complaintDto.setComplaint(complaint.getComplaint());
        return complaintDto;
    }

    private Complaint mapToComplaint(ComplaintDto complaintDto) {
        Complaint complaint = new Complaint();
        complaint.setMember(userRepository.findById(complaintDto.getUserID()).orElseThrow(
                () -> new RuntimeException("User not found with id " + complaintDto.getUserID())));
        complaint.setComplaint(complaintDto.getComplaint());
        return complaint;
    }
}

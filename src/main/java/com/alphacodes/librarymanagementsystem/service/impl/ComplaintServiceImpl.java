package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.ComplaintDto;
import com.alphacodes.librarymanagementsystem.DTO.ComplaintViewDto;
import com.alphacodes.librarymanagementsystem.Model.Complaint;
import com.alphacodes.librarymanagementsystem.repository.ComplaintRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.ComplaintService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public ComplaintServiceImpl(ComplaintRepository complaintRepository, UserRepository userRepository) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
    }

    // method to add a new complaint
    @Override
    public ComplaintDto addComplaint(ComplaintDto complaintDto) {
        Complaint complaint = mapToComplaint(complaintDto);
        Complaint newComplaint = complaintRepository.save(complaint);
        return mapToComplaintDto(newComplaint);
    }

    @Override
    public List<ComplaintViewDto> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();

        return complaints
                .stream()
                .map(this::mapToComplaintViewtDto)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteComplaint(Long complaintId) {
        complaintRepository.deleteById(complaintId);
        return "Complaint deleted Successfully";
    }

    @Override
    public List<ComplaintViewDto> getComplaintsByUserId(String userId) {
        Optional<List<Complaint>> complaintslist = complaintRepository.findByMember_UserID(userId);
        List<Complaint> complaints = complaintslist.orElseThrow(
                () -> new RuntimeException("Complaints not found with user id " + userId));

        return complaints
                .stream()
                .map(this::mapToComplaintViewtDto)
                .collect(Collectors.toList());
    }

    @Override
    public ComplaintDto updateComplaint(ComplaintDto complaintViewDto, Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(
                () -> new RuntimeException("Complaint not found with id " + complaintId));

        complaint.setComplaintType(complaintViewDto.getComplaintType());
        complaint.setComplaintDescription(complaintViewDto.getComplaintDescription());
        complaint.setComplaintDate(complaintViewDto.getComplaintDate());
        complaint.setComplaintTime(complaintViewDto.getComplaintTime());

        Complaint updatedComplaint = complaintRepository.save(complaint);
        return mapToComplaintDto(updatedComplaint);
    }

    @Override
    public ComplaintViewDto resolveComplaint(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(
                () -> new RuntimeException("Complaint not found with id " + complaintId));

        complaint.setResolved(true);

        Complaint updatedComplaint = complaintRepository.save(complaint);
        return mapToComplaintViewtDto(updatedComplaint);
    }

    @Override
    public ComplaintViewDto unresolveComplaint(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(
                () -> new RuntimeException("Complaint not found with id " + complaintId));

        complaint.setResolved(false);

        Complaint updatedComplaint = complaintRepository.save(complaint);
        return mapToComplaintViewtDto(updatedComplaint);
    }

    @Override
    public List<ComplaintViewDto> getUnresolvedComplaints() {
        List<Complaint> complaints = complaintRepository.findByResolved(false);

        return complaints
                .stream()
                .map(this::mapToComplaintViewtDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplaintViewDto> getResolvedComplaints() {
        List<Complaint> complaints = complaintRepository.findByResolved(true);

        return complaints
                .stream()
                .map(this::mapToComplaintViewtDto)
                .collect(Collectors.toList());
    }

    @Override
    public ComplaintViewDto getComplaintById(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(
                () -> new RuntimeException("Complaint not found with id " + complaintId));
        return mapToComplaintViewtDto(complaint);
    }

    private ComplaintDto mapToComplaintDto(Complaint complaint) {
        ComplaintDto complaintDto = new ComplaintDto();

        complaintDto.setUserID(complaint.getMember().getUserID());
        complaintDto.setComplaintType(complaint.getComplaintType());
        complaintDto.setComplaintDescription(complaint.getComplaintDescription());
        complaintDto.setComplaintDate(complaint.getComplaintDate());
        complaintDto.setComplaintTime(complaint.getComplaintTime());

        return complaintDto;
    }

    private Complaint mapToComplaint(ComplaintDto complaintDto) {
        Complaint complaint = new Complaint();

        complaint.setComplaintType(complaintDto.getComplaintType());
        complaint.setComplaintDescription(complaintDto.getComplaintDescription());
        complaint.setComplaintDate(complaintDto.getComplaintDate());
        complaint.setComplaintTime(complaintDto.getComplaintTime());
        complaint.setMember(userRepository.findByUserID(complaintDto.getUserID()));
        complaint.setResolved(false);

        return complaint;
    }

    private ComplaintViewDto mapToComplaintViewtDto(Complaint complaint) {
        ComplaintViewDto complaintViewDto = new ComplaintViewDto();

        complaintViewDto.setComplaintId(complaint.getComplaintId());
        complaintViewDto.setUserID(complaint.getMember().getUserID());
        complaintViewDto.setComplaintType(complaint.getComplaintType());
        complaintViewDto.setComplaintDescription(complaint.getComplaintDescription());
        complaintViewDto.setComplaintDate(complaint.getComplaintDate());
        complaintViewDto.setComplaintTime(complaint.getComplaintTime());
        complaintViewDto.setResolved(complaint.isResolved());

        return complaintViewDto;
    }
}

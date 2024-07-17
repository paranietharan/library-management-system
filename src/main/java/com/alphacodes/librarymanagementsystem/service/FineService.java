package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.FineDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FineService {
    double calculateFine(String memberId);
    public String settleFine(String memberId);
    List<FineDto> getAllUnpaidFine();
    List<FineDto> getFineHistoryByUser(String memberId);
    void checkAndUpdateFines();
    ResponseEntity<FineDto> getUnpaidFineByUser(String memberId);
}

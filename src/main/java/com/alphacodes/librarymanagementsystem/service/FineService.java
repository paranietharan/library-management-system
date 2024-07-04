package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.FineDto;

import java.util.List;

public interface FineService {
    double calculateFine(String memberId);
    public String settleFine(String memberId);
    List<FineDto> getAllUnpaidFine();
    List<FineDto> getFineHistoryByUser(String memberId);

    void checkAndUpdateFines();
}

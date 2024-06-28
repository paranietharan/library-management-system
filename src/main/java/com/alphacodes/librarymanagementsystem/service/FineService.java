package com.alphacodes.librarymanagementsystem.service;

public interface FineService {
    double calculateFine(int memberId);
    public String settleFine(int memberId);
}

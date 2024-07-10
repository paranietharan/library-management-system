package com.alphacodes.librarymanagementsystem.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class FineDto {
    private Long fineId;
    private double amount;
    private boolean paidStatus;
    private Date resourceIssueDate;
    private String memberId;
    private Long resourceId;
}

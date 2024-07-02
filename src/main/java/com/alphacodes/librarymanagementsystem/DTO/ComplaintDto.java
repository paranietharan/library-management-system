package com.alphacodes.librarymanagementsystem.DTO;

import lombok.Data;

@Data
public class ComplaintDto {
    private String userID;
    private String complaintDescription;;
    private String complaintType;
    private String complaintDate;
    private String complaintTime;
}

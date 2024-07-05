package com.alphacodes.librarymanagementsystem.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class IssueDto {
    private Long issueId;
    private Date date;
    private boolean returned;
    private boolean finePaid;
    private Long resourceId;
}

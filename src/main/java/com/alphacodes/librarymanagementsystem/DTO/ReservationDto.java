package com.alphacodes.librarymanagementsystem.DTO;

import lombok.Data;

@Data
public class ReservationDto {
    private Long reservationId;
    private String status;
    private String memberId;
    private Long bookId;
}

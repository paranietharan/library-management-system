package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.ReservationDto;

import java.util.List;

public interface ReservationService {
    public String reserveResource(Long resourceId, String userId);
    public void releaseExpiredReservations();

    // method to get all reservations
    List<ReservationDto> getAllReservations();

    // method to cancel reservation
    String cancelReservation(Long reservationId);

    // get past reservation history of a user
    List<ReservationDto> getPastReservationHistory(String userId);

    ReservationDto getUserActiveReservation(String userId);
}

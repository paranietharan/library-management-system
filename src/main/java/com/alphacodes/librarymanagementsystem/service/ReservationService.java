package com.alphacodes.librarymanagementsystem.service;

public interface ReservationService {
    public String reserveResource(Long resourceId, String userId);
    public void releaseExpiredReservations();
}

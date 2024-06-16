package com.alphacodes.librarymanagementsystem.service;

public interface ReservationService {
    public String reserveResource(Long resourceId, int userId);
    public void releaseExpiredReservations();
}

package com.alphacodes.librarymanagementsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationScheduler {

    @Autowired
    private ReservationServiceImpl reservationService;

    // method will be executed once every hour.
    // so that expired reservations are released for every one hour.
    @Scheduled(fixedRate = 3600000) // Every hour
    public void releaseExpiredReservations() {
        reservationService.releaseExpiredReservations();
    }
}


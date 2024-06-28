package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.service.impl.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationServiceImpl reservationService;

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveResource(@RequestParam Long resourceId, @RequestParam int userId) {
        String result = reservationService.reserveResource(resourceId, userId);
        return ResponseEntity.ok(result);
    }

    // Manually trigger the release of expired reservations
    // If any error happens todo- hot reload in front end
    @PostMapping("/releaseExpired")
    public ResponseEntity<String> releaseExpiredReservations() {
        reservationService.releaseExpiredReservations();
        return ResponseEntity.ok("Expired reservations released.");
    }
}

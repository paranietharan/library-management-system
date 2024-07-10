package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.ReservationDto;
import com.alphacodes.librarymanagementsystem.service.impl.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationServiceImpl reservationService;

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveResource(@RequestParam Long resourceId, @RequestParam String userId) {
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

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelReservation(@RequestParam Long reservationId) {
        String result = reservationService.cancelReservation(reservationId);
        return ResponseEntity.ok(result);
    }

    // get all active resevation for librarian
    @GetMapping("/active")
    public ResponseEntity<List<ReservationDto>> getActiveReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    // get all past reservations by user
    @GetMapping("/past")
    public ResponseEntity<List<ReservationDto>> getPastReservationHistory(String userId) {
        return ResponseEntity.ok(reservationService.getPastReservationHistory(userId));
    }

    // Get a user active reservation
    @GetMapping("active/user/{userId}")
    public ResponseEntity<ReservationDto> getUserActiveReservation(@PathVariable String userId) {
        return ResponseEntity.ok(reservationService.getUserActiveReservation(userId));
    }
}

package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.Model.Reservation;
import com.alphacodes.librarymanagementsystem.Model.Resource;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.repository.ReservationRepository;
import com.alphacodes.librarymanagementsystem.repository.ResourceRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    public String reserveResource(Long resourceId, int userId) {
        Optional<Resource> resourceOpt = resourceRepository.findById(resourceId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (resourceOpt.isPresent() && userOpt.isPresent()) {
            Resource resource = resourceOpt.get();
            User user = userOpt.get();

            if (resource.getAvailability() > 0) {
                resource.setAvailability(resource.getAvailability() - 1);
                resourceRepository.save(resource);

                Reservation reservation = new Reservation();
                reservation.setBook(resource);
                reservation.setMember(user);
                reservation.setReservationTime(LocalDateTime.now());
                reservation.setStatus("Active");
                reservationRepository.save(reservation);

                return "Resource reserved successfully for 24 hours.";
            } else {
                return "Resource is not available.";
            }
        } else {
            return "Resource or User not found.";
        }
    }

    @Transactional
    public void releaseExpiredReservations() {
        LocalDateTime expirationTime = LocalDateTime.now().minusHours(24);
        List<Reservation> expiredReservations = reservationRepository.findByReservationTimeBefore(expirationTime);

        for (Reservation reservation : expiredReservations) {
            Resource resource = reservation.getBook();
            resource.setAvailability(resource.getAvailability() + 1);
            resourceRepository.save(resource);

            reservation.setStatus("Expired");
            reservationRepository.save(reservation);
        }
    }
}

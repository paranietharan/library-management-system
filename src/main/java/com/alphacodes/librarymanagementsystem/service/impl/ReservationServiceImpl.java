package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.ReservationDto;
import com.alphacodes.librarymanagementsystem.EmailService.EmailService;
import com.alphacodes.librarymanagementsystem.Model.Fine;
import com.alphacodes.librarymanagementsystem.Model.Reservation;
import com.alphacodes.librarymanagementsystem.Model.Resource;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.repository.FineRepository;
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
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private FineRepository fineRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public String reserveResource(Long resourceId, String userId) {
        logger.info("Attempting to reserve resource with ID {} for user {}", resourceId, userId);
        try {
            Optional<Resource> resourceOpt = resourceRepository.findById(resourceId);
            Optional<User> userOpt = Optional.ofNullable(userRepository.findByUserID(userId));

            Fine fine = fineRepository.findByUserID(userId);
            if (fine != null && fine.getAmount() > 0) {
                logger.warn("User {} has unpaid fine. Cannot reserve resource.", userId);
                return "Please pay the fine first.";
            }

            Optional<Reservation> existingReservation = reservationRepository.findByMemberIdAndStatus(userId, "Active");
            if (existingReservation.isPresent()) {
                logger.warn("User {} already has an active reservation.", userId);
                return "You have already reserved a resource.";
            }

            if (resourceOpt.isPresent() && userOpt.isPresent()) {
                Resource resource = resourceOpt.get();
                User user = userOpt.get();

                if (resource.getNo_of_copies() > 0) {
                    resource.setNo_of_copies(resource.getNo_of_copies() - 1);
                    resourceRepository.save(resource);

                    Reservation reservation = new Reservation();
                    reservation.setBook(resource);
                    reservation.setMember(user);
                    reservation.setReservationTime(LocalDateTime.now());
                    reservation.setStatus("Active");
                    reservationRepository.save(reservation);

                    logger.info("Resource {} reserved successfully for user {}", resourceId, userId);

                    // send email to user about successful reservation
                    emailService
                            .sendSimpleEmail(
                                    user.getEmailAddress(),
                                    "Resource Reserved",
                                    "You reserve " + resource.getTitle() + " successfully."+
                                            "Your Reservation ID is: " + reservation.getReservationId() +
                                            "Your reservation will expire in 24 hours." +
                                            "Please collect the resource within 24 hours." +
                                            "\n\nThank you."
                            );
                    return "Resource reserved successfully for 24 hours.";
                } else {
                    logger.warn("Resource {} is not available.", resourceId);
                    return "Resource is not available.";
                }
            } else {
                logger.warn("Resource or User not found for resourceId {} and userId {}", resourceId, userId);
                return "Resource or User not found.";
            }
        } catch (Exception e) {
            logger.error("Error reserving resource: {}", e.getMessage(), e);
            return "An error occurred while reserving the resource.";
        }
    }

    @Transactional
    public void releaseExpiredReservations() {
        LocalDateTime expirationTime = LocalDateTime.now().minusHours(24);
        List<Reservation> expiredReservations = reservationRepository.findByReservationTimeBefore(expirationTime);

        for (Reservation reservation : expiredReservations) {
            Resource resource = reservation.getBook();
            resource.setNo_of_copies(resource.getNo_of_copies() + 1);
            resourceRepository.save(resource);

            reservation.setStatus("Expired");
            reservationRepository.save(reservation);

            logger.info("Released expired reservation {} for resource {}", reservation.getReservationId(), resource.getId());
        }
    }

    // method to get all reservations
    @Override
    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAllActiveReservations();
        return reservations.stream().map(reservation -> {
            ReservationDto dto = new ReservationDto();
            dto.setReservationId(reservation.getReservationId());
            dto.setStatus(reservation.getStatus());
            dto.setMemberId(reservation.getMember().getUserID());
            dto.setBookId(reservation.getBook().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public String cancelReservation(Long reservationId) {
        logger.info("Attempting to cancel reservation with ID {}", reservationId);
        try {
            Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);

            if (reservationOpt.isPresent()) {
                Reservation reservation = reservationOpt.get();
                Resource resource = reservation.getBook();
                resource.setNo_of_copies(resource.getNo_of_copies() + 1);
                resourceRepository.save(resource);

                reservation.setStatus("Cancelled");
                reservationRepository.save(reservation);

                logger.info("Reservation {} cancelled successfully.", reservationId);

                // notify user about successful cancellation
                emailService
                        .sendSimpleEmail(
                                reservation.getMember().getEmailAddress(),
                                "Reservation Cancelled",
                                "Your reservation for " + resource.getTitle() + " has been cancelled." +
                                        "\n\nThank you."
                        );
                return "Reservation cancelled successfully.";
            } else {
                logger.warn("Reservation not found for ID {}", reservationId);
                return "Reservation not found.";
            }
        } catch (Exception e) {
            logger.error("Error cancelling reservation: {}", e.getMessage(), e);
            return "An error occurred while cancelling the reservation.";
        }
    }

    @Override
    public List<ReservationDto> getPastReservationHistory(String userId) {
        List<Reservation> reservations = reservationRepository.findPastReservationsByUserId(userId);

        return reservations.stream().map(reservation -> {
            ReservationDto dto = new ReservationDto();
            dto.setReservationId(reservation.getReservationId());
            dto.setStatus(reservation.getStatus());
            dto.setMemberId(reservation.getMember().getUserID());
            dto.setBookId(reservation.getBook().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public ReservationDto getUserActiveReservation(String userId) {
        Optional<Reservation> reservationOpt = reservationRepository.findByMemberIdAndStatus(userId, "Active");

        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();
            ReservationDto dto = new ReservationDto();
            dto.setReservationId(reservation.getReservationId());
            dto.setStatus(reservation.getStatus());
            dto.setMemberId(reservation.getMember().getUserID());
            dto.setBookId(reservation.getBook().getId());
            return dto;
        } else {
            return null;
        }
    }
}

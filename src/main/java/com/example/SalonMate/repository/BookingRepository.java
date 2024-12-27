package com.example.SalonMate.repository;

import com.example.SalonMate.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Custom query to find all Bookings by a specific user
    List<Booking> findByUserId(Long userId);
    
    // Custom query to find a Booking by its booking number
    Optional<Booking> findByBookingNumber(String bookingNumber);

    List<Booking> findByUserIdAndStatusNot(Long userId, String status);

    String deleteByUserIdAndId(Long userId, Long bookingId);
}

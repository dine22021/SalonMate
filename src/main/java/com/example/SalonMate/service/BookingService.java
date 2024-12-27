package com.example.SalonMate.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SalonMate.model.Booking;
import com.example.SalonMate.model.User;
import com.example.SalonMate.repository.BookingRepository;
import com.example.SalonMate.repository.UserRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    public String bookSlot(Long userId, String bookingNumber, LocalDate bookingDate, LocalTime timeSlot) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return "User not found.";
        }

        User user = userOpt.get();

        if (isSlotOverlapping(user, bookingDate, timeSlot)) {
            return "This slot is already booked by you. Please choose a different time.";
        }

        Booking booking = new Booking(bookingNumber, bookingDate, timeSlot, user);
        bookingRepository.save(booking);

        return "Booking successful!";
    }

    private boolean isSlotOverlapping(User user, LocalDate bookingDate, LocalTime timeSlot) {
        List<Booking> existingBookings = bookingRepository.findByUserId(user.getId());

        for (Booking booking : existingBookings) {
            if (booking.getBookingDate().isEqual(bookingDate) && booking.getTimeSlot().equals(timeSlot)) {
                return true;
            }
        }

        return false;
    }

    public List<Booking> viewBookings(Long userId) {
        return bookingRepository.findByUserIdAndStatusNot(userId, "CANCELLED");
    }

    public boolean cancelBooking(Long userId, Long bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            if (booking.getUser().getId().equals(userId)) {
                booking.setStatus("CANCELLED");
                bookingRepository.save(booking);
                return true;
            }
        }

        return false;
    }
}
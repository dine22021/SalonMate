package com.example.SalonMate.controller;

import com.example.SalonMate.model.Booking;
import com.example.SalonMate.model.User;
import com.example.SalonMate.service.BookingService;
import com.example.SalonMate.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @GetMapping("/book-slot")
    public String showBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "create-booking";
    }

    @PostMapping("/book-slot")
    public String createBooking(@RequestParam("username") String username,
                                @RequestParam("bookingDate") LocalDate bookingDate,
                                @RequestParam("timeSlot") LocalTime timeSlot,
                                Model model) {

        User user = userService.findUserByUsername(username).orElse(null);
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "create-booking";
        }

        String bookingResult = bookingService.bookSlot(user.getId(), "Booking_" + System.currentTimeMillis(), bookingDate, timeSlot);

        if ("Booking successful!".equals(bookingResult)) {
            model.addAttribute("success", bookingResult);
            return "redirect:/my-bookings";
        } else {
            model.addAttribute("error", bookingResult);
            return "create-booking";
        }
    }

    @GetMapping("/my-bookings")
    public String viewUserBookings(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/login";
        }

        User user = userService.findUserByUsername(username).orElse(null);
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "my-bookings";
        }

        List<Booking> bookings = bookingService.viewBookings(user.getId());
        model.addAttribute("bookings", bookings);
        model.addAttribute("username", username);

        return "my-bookings";
    }

    @PostMapping("/cancel")
    public String cancelBooking(@RequestParam("username") String username,
                                @RequestParam("bookingId") Long bookingId,
                                HttpSession session,
                                Model model) {
        User user = userService.findUserByUsername(username).orElse(null);

        if (user == null || !username.equals(session.getAttribute("username"))) {
            model.addAttribute("error", "Unauthorized access.");
            return "redirect:/my-bookings";
        }

        boolean isCancelled = bookingService.cancelBooking(user.getId(), bookingId);

        if (!isCancelled) {
            model.addAttribute("error", "Unable to cancel booking.");
        }

        return "redirect:/my-bookings";
    }
}
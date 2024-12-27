package com.example.SalonMate.model;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String bookingNumber;

    @Column(nullable = false)
    private LocalDate bookingDate;

    @Column(nullable = false)
    private LocalTime timeSlot;

    private String status="BOOKED";

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Default constructor
    public Booking() {}

    // Constructor without id
    public Booking(String bookingNumber, LocalDate bookingDate, LocalTime timeSlot, User user) {
        this.bookingNumber = bookingNumber;
        this.bookingDate = bookingDate;
        this.timeSlot = timeSlot;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalTime getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(LocalTime timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

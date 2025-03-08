package com.travel.management.controller;

import com.travel.management.model.Booking;
import com.travel.management.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;
    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/trip")
    public Booking bookTrip(@RequestParam Long userId, @RequestParam Long tripId, @RequestParam LocalDate bookingDate) {
        return bookingService.bookTrip(userId, tripId, bookingDate);
    }

    /*@PostMapping("/package")
    public Booking bookPackage(@RequestParam Long userId, @RequestParam Long packageId, @RequestParam String bookingDate) {
        return bookingService.bookPackage(userId, packageId, bookingDate);
    }*/

    @GetMapping("/{userId}")
    public List<Booking> getUserBookings(@PathVariable Long userId) {
        return bookingService.getUserBookings(userId);
    }
}


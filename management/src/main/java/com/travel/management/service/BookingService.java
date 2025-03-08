package com.travel.management.service;

import com.travel.management.model.Booking;
import com.travel.management.model.Package;
import com.travel.management.model.Trip;
import com.travel.management.model.User;
import com.travel.management.repository.BookingRepository;
import com.travel.management.repository.PackageRepository;
import com.travel.management.repository.TripRepository;
import com.travel.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private PackageRepository packageRepository;
    @Autowired
    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, TripRepository tripRepository, PackageRepository packageRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
        this.packageRepository = packageRepository;
    }

    public Booking bookTrip(Long userId, Long tripId, LocalDate bookingDate) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found"));

        // Prevent duplicate booking for the same date
        if (bookingRepository.existsByUserAndTripAndBookingDate(user, trip, bookingDate)) {
            throw new RuntimeException("You have already booked this trip for the same date.");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTrip(trip);
        booking.setBookingDate(bookingDate);
        booking.setStatus("Pending");

        return bookingRepository.save(booking);
    }

   /* public Booking bookPackage(Long userId, Long packageId, String bookingDate) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Package bookedPackage = packageRepository.findById(packageId).orElseThrow(() -> new RuntimeException("Package not found"));

        if (bookingRepository.existsByUserAndBookedPackageAndBookingDate(user, bookedPackage, bookingDate)) {
            throw new RuntimeException("You have already booked this package for the same date.");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBookedPackage(bookedPackage);
        booking.setBookingDate(LocalDate.parse(bookingDate));
        booking.setStatus("Pending");

        return bookingRepository.save(booking);
    }*/

    public List<Booking> getUserBookings(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getBookings();
    }
}



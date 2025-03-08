package com.travel.management.repository;

import com.travel.management.model.Booking;
import com.travel.management.model.Trip;
import com.travel.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByUserAndTripAndBookingDate(User user, Trip trip, LocalDate bookingDate);
    //boolean existsByUserAndBookedPackageAndBookingDate(User user, Package bookedPackage, String bookingDate);
}



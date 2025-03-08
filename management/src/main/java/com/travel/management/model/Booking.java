package com.travel.management.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class Booking {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "booking_id")
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "trip_id", nullable = true)
        private Trip trip;

        @ManyToOne
        @JoinColumn(name = "package_id", nullable = true)
        private Package bookedPackage;

        private LocalDate bookingDate;

        @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
        private Payment payment;

    private String status;
        public Booking() {
        }

    public Booking(Long id, User user, Trip trip, Package bookedPackage, LocalDate bookingDate, Payment payment, String status) {
        this.id = id;
        this.user = user;
        this.trip = trip;
        this.bookedPackage = bookedPackage;
        this.bookingDate = bookingDate;
        this.payment = payment;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Package getBookedPackage() {
        return bookedPackage;
    }

    public void setBookedPackage(Package bookedPackage) {
        this.bookedPackage = bookedPackage;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", user=" + user +
                ", trip=" + trip +
                ", bookedPackage=" + bookedPackage +
                ", bookingDate=" + bookingDate +
                ", payment=" + payment +
                '}';
    }
}

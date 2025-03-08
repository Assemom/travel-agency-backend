package com.travel.management.model;

import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "package_id")
    private Package pkg;

    // Validation: Ensure review links to either Trip or Package
    @PrePersist
    @PreUpdate
    public void validate() {
        if (trip == null && pkg == null) {
            throw new IllegalStateException("Review must link to a Trip or Package.");
        }
        if (trip != null && pkg != null) {
            throw new IllegalStateException("Review cannot link to both Trip and Package.");
        }
    }

    //getters and setters and constructor

    public Review() {
    }

    public Review(Long reviewId, User user, Trip trip, Package pkg) {
        this.reviewId = reviewId;
        this.user = user;
        this.trip = trip;
        this.pkg = pkg;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public void setPkg(Package pkg) {
        this.pkg = pkg;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", user=" + user +
                ", trip=" + trip +
                ", pkg=" + pkg +
                '}';
    }
}

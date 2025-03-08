package com.travel.management.repository;

import com.travel.management.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    //List<Review> findByTrip(Trip trip);
    //List<Review> findByPackage(Package pkg);
}

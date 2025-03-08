package com.travel.management.repository;

import com.travel.management.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    //Page<Trip> findByAvailableTrue(Pageable pageable); // For paginated available trips
}

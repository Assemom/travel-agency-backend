package com.travel.management.repository;

import com.travel.management.model.Package;
import com.travel.management.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    boolean existsByTripsContainingAndId(Trip trip, Long packageId); // Check for duplicate trips
}

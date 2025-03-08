package com.travel.management.service;

import com.travel.management.model.Trip;
import com.travel.management.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class TripService {

    private final TripRepository tripRepository;
    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<Trip>getAllTrips(){
        return tripRepository.findAll();
    }
    public Trip getTripById(Long id){
        return tripRepository.findById(id).orElse(null);
    }
    public Trip addTrip(Trip trip) throws IOException{
        return tripRepository.save(trip);
    }
    public void deleteById(Long id){
        tripRepository.deleteById(id);
    }
}

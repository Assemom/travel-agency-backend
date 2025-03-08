package com.travel.management.controller;

import com.travel.management.model.Trip;
import com.travel.management.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TripController {
    private final TripService tripService;
    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/trips") // to all the roles
    public List<Trip> getAllTrips(){
        return tripService.getAllTrips();
    }

    @GetMapping("/trips/{tripId}")  // to all users
    public Trip getTripById(@PathVariable Long tripId){
        return tripService.getTripById(tripId);
    }
    @PostMapping("/trips") //to manager and admin
    public Trip addTrip(@RequestBody Trip trip){
        try {
            return tripService.addTrip(trip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/trips/{id}") //to manager and admin
    public ResponseEntity<String> updateTrip(@PathVariable int id , @RequestBody Trip trip){
        try {
            Trip currTrip = tripService.addTrip(trip);
        } catch (IOException e) {
            return new ResponseEntity<>("cannot be updated", HttpStatus.BAD_REQUEST);
        }
        if (trip != null){
            return new ResponseEntity<>("updated successfully" , HttpStatus.OK);
        }else{
            return new ResponseEntity<>("failed to update" , HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/trips/{tripId}") //to admin only
    public void deleteTripById(@PathVariable Long tripId){
        tripService.deleteById(tripId);
    }

}

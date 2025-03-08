package com.travel.management.controller;


import com.travel.management.model.Review;
import com.travel.management.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews") // to all the roles
    public List<Review> getAllReviews(){
        return reviewService.getAllReviews();
    }

    @PostMapping("/reviews") //to tourists only
    public Review addReview(@RequestBody Review review){
        try {
            return reviewService.addReview(review);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @PutMapping("/reviews/{id}") //to tourists only
    public ResponseEntity<String> updateReview(@PathVariable int id , @RequestBody Review review){
        try {
            Review currReview = reviewService.addReview(review);
        } catch (IOException e) {
            return new ResponseEntity<>("cannot be updated", HttpStatus.BAD_REQUEST);
        }
        if (review != null){
            return new ResponseEntity<>("updated successfully" , HttpStatus.OK);
        }else{
            return new ResponseEntity<>("failed to update" , HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/reviews/{reviewId}") //to all the roles for managements
    public void deleteReviewById(@PathVariable Long reviewId){
        reviewService.deleteById(reviewId);
    }
}

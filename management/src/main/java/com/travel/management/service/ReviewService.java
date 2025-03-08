package com.travel.management.service;

import com.travel.management.model.Review;
import com.travel.management.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAllReviews(){

        return reviewRepository.findAll();
    }

    public Review addReview(Review review) throws IOException {
        return reviewRepository.save(review);
    }
    public void deleteById(Long id){
        reviewRepository.deleteById(id);
    }
}

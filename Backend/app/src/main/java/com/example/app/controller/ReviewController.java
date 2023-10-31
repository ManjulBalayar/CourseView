package com.example.app.controller;

import com.example.app.model.Course;
import com.example.app.model.Review;
import com.example.app.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
public class ReviewController {

    @Autowired
    ReviewRepository repo;

    // POST METHODS //
    @PostMapping("/review")
    public Review addReview(@RequestBody Review review) {
        repo.save(review);
        return review;
    }


    // GET METHODS //

    @GetMapping("/reviews/byUser/{userId}")
    public List<Review> getReviewsByUserId(@PathVariable Long userId) {
        return repo.findByUserProfileUserid(userId);
    }
    @GetMapping("/review/{review_id}")
    public Optional<Review> getReview(@PathVariable("review_id") Long review_id) {
        return repo.findById(review_id);
    }
    @GetMapping("/reviews")
    public List<Review> getReviews() {
        return repo.findAll();
    }
}

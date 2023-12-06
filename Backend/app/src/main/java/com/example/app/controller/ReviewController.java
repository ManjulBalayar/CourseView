package com.example.app.controller;

import com.example.app.model.Review;
import com.example.app.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * ReviewController class manages HTTP requests for Review resources.
 * It interacts with ReviewRepository to perform CRUD operations on Review entities.
 */
@RestController
public class ReviewController {

    @Autowired
    ReviewRepository repo;

    /**
     * Adds a new Review entity to the database.
     *
     * @param review the Review entity to be added
     * @return the added Review entity
     */
    @PostMapping("/review")
    public Review addReview(@RequestBody Review review) {
        repo.save(review);
        return review;
    }


    /**
     * Retrieves a list of Review entities associated with a specific user ID.
     *
     * @param userId the user ID to filter the Review entities
     * @return a list of Review entities related to the specified user ID
     */
    @GetMapping("/reviews/byUser/{userId}")
    public List<Review> getReviewsByUserId(@PathVariable Long userId) {
        return repo.findByUserProfileUserid(userId);
    }

    /**
     * Retrieves a Review entity by its ID.
     *
     * @param review_id the ID of the Review entity to retrieve
     * @return an Optional containing the Review entity if found, or an empty Optional if not found
     */
    @GetMapping("/review/{review_id}")
    public Optional<Review> getReview(@PathVariable("review_id") Long review_id) {
        return repo.findById(review_id);
    }

    /**
     * Retrieves all Review entities in the database.
     *
     * @return a list of all Review entities
     */
    @GetMapping("/reviews")
    public List<Review> getReviews() {
        return repo.findAll();
    }
}

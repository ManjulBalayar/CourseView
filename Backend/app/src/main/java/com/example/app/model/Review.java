package com.example.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;

    private String review_comment;

    private Long review_rating;

    private Long review_difficulty;

    private Long review_time_commitment;

    public Long getReview_difficulty() {
        return review_difficulty;
    }

    public void setReview_difficulty(Long review_difficulty) {
        this.review_difficulty = review_difficulty;
    }

    public Long getReview_time_commitment() {
        return review_time_commitment;
    }

    public void setReview_time_commitment(Long review_time_commitment) {
        this.review_time_commitment = review_time_commitment;
    }

    public String getReview_comment() {
        return review_comment;
    }

    public void setReview_comment(String review_comment) {
        this.review_comment = review_comment;
    }

    public Long getReview_rating() {
        return review_rating;
    }

    public void setReview_rating(Long review_rating) {
        this.review_rating = review_rating;
    }

    public void setReview_id(Long reviewId) {
        this.review_id = reviewId;
    }

    public Long getReview_id() {
        return review_id;
    }
}

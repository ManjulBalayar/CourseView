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

    private String comment;

    private Long rating;

    private Long difficulty;

    private Long time_commitment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Long difficulty) {
        this.difficulty = difficulty;
    }

    public Long getTime_commitment() {
        return time_commitment;
    }

    public void setTime_commitment(Long time_commitment) {
        this.time_commitment = time_commitment;
    }

    public void setReview_id(Long reviewId) {
        this.review_id = reviewId;
    }

    public Long getReview_id() {
        return review_id;
    }
}

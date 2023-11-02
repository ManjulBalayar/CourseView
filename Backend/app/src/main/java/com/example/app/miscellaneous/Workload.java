package com.example.app.miscellaneous;

public class Workload {

    private Long rating;

    private Long difficulty;

    private Long time_commitment;

    public Workload(Long rating, Long difficulty, Long time_commitment) {
        this.rating = rating;
        this.difficulty = difficulty;
        this.time_commitment = time_commitment;
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
}

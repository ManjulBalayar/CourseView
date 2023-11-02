package com.example.app.miscellaneous;

public class Workload {

    private Float rating;

    private Float difficulty;

    private Float time_commitment;

    public Workload(Float rating, Float difficulty, Float time_commitment) {
        this.rating = rating;
        this.difficulty = difficulty;
        this.time_commitment = time_commitment;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Float getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Float difficulty) {
        this.difficulty = difficulty;
    }

    public Float getTime_commitment() {
        return time_commitment;
    }

    public void setTime_commitment(Float time_commitment) {
        this.time_commitment = time_commitment;
    }
}

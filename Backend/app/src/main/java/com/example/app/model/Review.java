package com.example.app.model;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;

    private String comment;

    private Long rating;

    private Long difficulty;

    private Long time_commitment;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "courseid")
    private Course course;


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

    // Getter and setter for userId
    public Long getUserId() {
        return userProfile != null ? userProfile.getUser_id() : null;
    }

    public void setUserId(Long userId) {
        if (userProfile == null) {
            userProfile = new UserProfile();
        }
        userProfile.setUser_id(userId);
    }

    // Getter and setter for courseId
    public Long getCourseId() {
        return course != null ? course.getCourse_id() : null;
    }

    public void setCourseId(Long courseId) {
        if (course == null) {
            course = new Course();
        }
        course.setCourse_id(courseId);
    }

    public Course[] getCourse() {
        return new Course[]{course};
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

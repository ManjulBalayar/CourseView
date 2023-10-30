package com.example.app.repository;

import com.example.app.model.Course;
import com.example.app.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.userProfile.userid = :userId")
    List<Review> findByUserId(Long userId);
}

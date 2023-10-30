package com.example.app.repository;

import com.example.app.model.Course;
import com.example.app.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}

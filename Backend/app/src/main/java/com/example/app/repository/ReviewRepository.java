package com.example.app.repository;

import com.example.app.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserProfileUserid(Long userId);

    List<Review> findByCourseCourseid(long courseId);
}

package com.example.app.repository;

import com.example.app.model.Review;
import com.example.app.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Review> findByUserProfileUserid(Long userId);
}
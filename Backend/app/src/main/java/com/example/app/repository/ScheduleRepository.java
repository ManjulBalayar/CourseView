package com.example.app.repository;

import com.example.app.model.Review;
import com.example.app.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
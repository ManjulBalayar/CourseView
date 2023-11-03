package com.example.app.repository;

import com.example.app.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByNameStartingWithOrderByName(String name);
}

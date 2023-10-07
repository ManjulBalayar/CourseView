package com.example.app.repository;

import com.example.app.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("FROM Course WHERE courseName LIKE ?1% ORDER BY courseName")
    List<Course> findByNameStartsWithSorted(String courseName);

}
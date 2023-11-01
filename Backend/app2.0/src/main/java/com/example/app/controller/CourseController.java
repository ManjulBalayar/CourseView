package com.example.app.controller;

import com.example.app.model.Course;
import com.example.app.model.Student;
import com.example.app.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class CourseController {

    @Autowired
    CourseRepository repo;

    // POST METHODS
    @PostMapping("/course")
    public Course addAlien(@RequestBody Course course) {
        repo.save(course);
        return course;
    }
    // END of POST METHODS


    // GET METHODS

    // Get certain course
    @GetMapping("/course/{course_id}")
    public Optional<Course> getStudent(@PathVariable("course_id") Long course_id) {
        return repo.findById(course_id);
    }

    // Get certain course student list
    @GetMapping("/course/{course_id}/students")
    public Set<Student> getCoursesLikedByStudent(@PathVariable("course_id") Long course_id) {
        Course course = repo.findById(course_id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return course.getLikes();
    }

    @GetMapping("/courses")
    public List<Course> getAliens() {

        return repo.findAll();
    }

    @GetMapping("/courses/{search}")
    public List<Course> getSearched(@PathVariable("search") String search) {

        return repo.findByNameStartsWithSorted(search);
    }


    // END of GET METHODS


}
package com.example.app.controller;

import com.example.app.model.Course;
import com.example.app.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * CourseController class manages HTTP requests for Course resources.
 * It interacts with CourseRepository to perform CRUD operations on Course entities.
 */
@RestController
public class CourseController {

    @Autowired
    CourseRepository repo;

    /**
     * Adds a new Course entity to the database.
     *
     * @param course the Course entity to be added
     * @return the added Course entity
     */
    @PostMapping("/course")
    public Course addAlien(@RequestBody Course course) {
        repo.save(course);
        return course;
    }

    /**
     * Retrieves a Course entity by its ID.
     *
     * @param course_id the ID of the Course entity to retrieve
     * @return an Optional containing the Course entity if found, or an empty Optional if not found
     */
    @GetMapping("/course/{course_id}")
    public Optional<Course> getStudent(@PathVariable("course_id") Long course_id) {
        return repo.findById(course_id);
    }

    /**
     * Retrieves all Course entities in the database.
     *
     * @return a list of all Course entities
     */
    @GetMapping("/courses")
    public List<Course> getAliens() {

        return repo.findAll();
    }

    /**
     * Retrieves a list of Course entities that start with a specified search string.
     *
     * @param search the search string to filter Course names
     * @return a list of Course entities matching the search criteria
     */
    @GetMapping("/courses/{search}")
    public List<Course> getSearched(@PathVariable("search") String search) {

        return repo.findByNameStartingWithOrderByName(search);
    }

}
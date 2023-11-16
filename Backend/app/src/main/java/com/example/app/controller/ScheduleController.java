package com.example.app.controller;

import com.example.app.miscellaneous.AddCourse;
import com.example.app.model.Course;
import com.example.app.model.Schedule;
import com.example.app.repository.ScheduleRepository;
import com.example.app.service.ScheduleCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ScheduleController manages HTTP requests related to Schedules and Courses.
 * It uses ScheduleRepository for CRUD operations on Schedule entities and
 * ScheduleCourseService for handling course scheduling.
 */
@RestController
public class ScheduleController {

    @Autowired
    ScheduleRepository repo;

    @Autowired
    private ScheduleCourseService scheduleCourseService;

    /**
     * Adds a course to a student's schedule by creating a link between the student and the course.
     *
     * @param addCourse Contains the information for adding a course (user ID and course ID).
     * @return The AddCourse object with the link details.
     */
    @PostMapping("/addCourse")
    public AddCourse addLike(@RequestBody AddCourse addCourse) {

        scheduleCourseService.addStudentLikesCourse(addCourse.getUser_id(),addCourse.getCourse_id());

        return addCourse;
    }

    /**
     * Adds a new Schedule entity to the database.
     *
     * @param schedule The Schedule entity to be added.
     * @return The added Schedule entity.
     */
    @PostMapping("/schedule")
    public Schedule addReview(@RequestBody Schedule schedule) {
        repo.save(schedule);
        return schedule;
    }

    /**
     * Retrieves a list of Course entities associated with a specific Schedule ID.
     *
     * @param schedule_id The Schedule ID to retrieve courses for.
     * @return A list of Course entities.
     */
    @GetMapping("/schedule/{schedule_id}")
    public List<Course> getSchedule(@PathVariable("schedule_id") Long schedule_id) {

        Schedule schedule = repo.findById(schedule_id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return schedule.getCourses();
    }

    /**
     * Retrieves all Schedule entities in the database.
     *
     * @return A list of all Schedule entities.
     */
    @GetMapping("/schedules")
    public List<Schedule> getSchedules() {
        return repo.findAll();
    }

}
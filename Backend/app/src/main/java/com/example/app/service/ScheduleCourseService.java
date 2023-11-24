package com.example.app.service;

import com.example.app.model.Course;
import com.example.app.model.Schedule;
import com.example.app.repository.CourseRepository;
import com.example.app.repository.ReviewRepository;
import com.example.app.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ScheduleCourseService provides services related to managing courses in schedules.
 * It interacts with ScheduleRepository and CourseRepository for operations on schedules and courses.
 */
@Service
public class ScheduleCourseService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Adds a course to a student's schedule. The method updates both the schedule and course entities.
     *
     * @param studentId The ID of the student whose schedule is being updated.
     * @param courseId The ID of the course to be added to the student's schedule.
     * @throws RuntimeException if either the student or the course is not found.
     */
    public void addStudentLikesCourse(Long studentId, Long courseId) {
        Schedule schedule = scheduleRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        schedule.getCourses().add(course);
        course.getSchedules().add(schedule);

        scheduleRepository.save(schedule);
        courseRepository.save(course);

    }
}

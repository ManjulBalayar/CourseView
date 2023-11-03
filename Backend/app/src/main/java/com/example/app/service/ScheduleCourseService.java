package com.example.app.service;

import com.example.app.model.Course;
import com.example.app.model.Schedule;
import com.example.app.repository.CourseRepository;
import com.example.app.repository.ReviewRepository;
import com.example.app.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleCourseService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ReviewRepository reviewRepository;

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

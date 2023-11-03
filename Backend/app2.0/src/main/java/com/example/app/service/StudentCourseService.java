package com.example.app.service;

import com.example.app.model.Course;
import com.example.app.model.Student;
import com.example.app.repository.CourseRepository;
import com.example.app.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCourseService {

    @Autowired
    private StudentRepo studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public void addStudentLikesCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        student.getCourses().add(course);
        course.getLikes().add(student);

        studentRepository.save(student);
        courseRepository.save(course);
    }
}
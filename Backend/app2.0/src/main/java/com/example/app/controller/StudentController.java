package com.example.app.controller;

import com.example.app.model.Course;
import com.example.app.model.Student;
import com.example.app.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
public class StudentController {

    @Autowired
    StudentRepo repo;

    // POST METHODS

    @PostMapping("/student")
    public Student addAlien(@RequestBody Student student) {
        repo.save(student);
        return student;
    }

    // END of POST METHODS




    // GET METHODS

//    // Get certain student
//    @GetMapping("/student")
//    public Optional<Student> getStudent(@RequestBody Map<String, Object> requestBody) {
//        Long student_id = Long.valueOf(requestBody.get("student_id").toString());
//        return repo.findById(student_id);
//    }
//
//    // Get certain student course list
//    @PostMapping("/student/courses")
//    public Set<Course> getCoursesLikedByStudent(@RequestBody Map<String, Object> requestBody) {
//        Long student_id = Long.valueOf(requestBody.get("student_id").toString());
//
//        Student student = repo.findById(student_id)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        return student.getCourses();
//    }

    // Gets all students
    @GetMapping("/students")
    public List<Student> getAliens() {

        return repo.findAll();
    }

    @GetMapping("/student/{student_id}")
    public Optional<Student> getStudent(@PathVariable("student_id") Long student_id) {
        return repo.findById(student_id);
    }

    // Get certain student course list
    @GetMapping("/student/{student_id}/courses")
    public Set<Course> getCoursesLikedByStudent(@PathVariable("student_id") Long student_id) {

        Student student = repo.findById(student_id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return student.getCourses();
    }

    // Gets all students names only
    @GetMapping("/students/names")
    public List<Map<String, Object>> getNames() {
        return repo.findAllStudentNames();
    }

    // Gets all students that start with "search"
    @GetMapping("/students/{search}")
    public List<Student> getSearched(@PathVariable("search") String search) {
        return repo.findByNameStartsWithSorted(search);
    }

    // END of GET METHODS
}

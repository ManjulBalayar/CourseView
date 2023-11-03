package com.example.app.controller;

import com.example.app.model.AddCourse;
import com.example.app.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddCourseController {

    @Autowired
    private StudentCourseService studentCourseService;


    // this will add a course by linking two objects(student, course)
    @PostMapping("/addCourse")
    public AddCourse addLike(@RequestBody AddCourse addCourse) {

        studentCourseService.addStudentLikesCourse(addCourse.getStudent_id(),addCourse.getCourse_id());

        return addCourse;
    }

}

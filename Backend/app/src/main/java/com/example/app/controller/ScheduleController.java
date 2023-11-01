package com.example.app.controller;

import com.example.app.miscellaneous.AddCourse;
import com.example.app.model.Schedule;
import com.example.app.repository.ScheduleRepository;
import com.example.app.service.ScheduleCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
public class ScheduleController {

    @Autowired
    ScheduleRepository repo;

    @Autowired
    private ScheduleCourseService scheduleCourseService;

    // POST METHODS //

    // this will add a course by linking two objects(student, course)
    @PostMapping("/addCourse")
    public AddCourse addLike(@RequestBody AddCourse addCourse) {

        scheduleCourseService.addStudentLikesCourse(addCourse.getUser_id(),addCourse.getCourse_id());

        return addCourse;
    }

    @PostMapping("/schedule")
    public Schedule addReview(@RequestBody Schedule schedule) {
        repo.save(schedule);
        return schedule;
    }


    // GET METHODS //
    @GetMapping("/schedule/{schedule_id}")
    public Optional<Schedule> getSchedule(@PathVariable("schedule_id") Long schedule_id) {
        return repo.findById(schedule_id);
    }
    @GetMapping("/schedules")
    public List<Schedule> getSchedules() {
        return repo.findAll();
    }

}
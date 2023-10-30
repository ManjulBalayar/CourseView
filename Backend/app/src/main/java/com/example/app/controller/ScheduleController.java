package com.example.app.controller;

import com.example.app.model.Course;
import com.example.app.model.Review;
import com.example.app.model.Schedule;
import com.example.app.repository.ReviewRepository;
import com.example.app.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
public class ScheduleController {

    @Autowired
    ScheduleRepository repo;

    // POST METHODS //
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
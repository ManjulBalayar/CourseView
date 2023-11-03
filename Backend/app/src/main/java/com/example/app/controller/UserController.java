package com.example.app.controller;

import com.example.app.model.Course;
import com.example.app.model.Schedule;
import com.example.app.model.UserProfile;
import com.example.app.repository.ScheduleRepository;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @GetMapping("/users/{user_id}")
    public Optional<UserProfile> getStudent(@PathVariable("user_id") Long user_id) {
        return userRepository.findById(user_id);
    }

    @GetMapping({"users/all"})
    List<UserProfile> GetAllUsers() {
        return this.userRepository.findAll();
    }

    @PostMapping({"users/post"})
    UserProfile PostUserByBody(@RequestBody UserProfile newUser) {
        this.userRepository.save(newUser);
        Schedule emptySchedule = new Schedule();
        emptySchedule.setUserProfile(newUser);
        scheduleRepository.save(emptySchedule);
        return newUser;
    }


}

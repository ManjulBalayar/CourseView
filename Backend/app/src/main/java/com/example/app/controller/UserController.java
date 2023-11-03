package com.example.app.controller;

import com.example.app.model.Schedule;
import com.example.app.model.UserProfile;
import com.example.app.repository.ScheduleRepository;
import com.example.app.repository.UserRepository;
import com.example.app.repository.UsernameAndId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/userprofiles")
    public List<UserProfile> getAllUserProfiles() {
        return userRepository.findAll();
    }

    @GetMapping("/userprofile/{userid}")
    public Optional<UserProfile> getUserProfile(@PathVariable("userid") Long userid) {
        return userRepository.findById(userid);
    }

    // Gets all user names only
    @GetMapping("/userprofiles/names")
    public List<UsernameAndId> getNames() {
        return userRepository.findAllByOrderByUsername();
    }

    // Gets all users that start with "search"
    @GetMapping("/userprofiles/{search}")
    public List<UserProfile> getSearched(@PathVariable("search") String search) {
        return userRepository.findByUsernameStartingWithOrderByUsername(search);
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

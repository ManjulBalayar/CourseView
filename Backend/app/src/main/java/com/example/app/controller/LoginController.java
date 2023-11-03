package com.example.app.controller;

import com.example.app.model.UserProfile;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users/{username}")
    public List<UserProfile> getStudent(@PathVariable("username") String username) {
        return userRepository.findAllByUsername(username);
    }

    @PostMapping("/login")
    public UserProfile getUser(@RequestBody Map<String, Object> requestBody) {

        String userName = requestBody.get("username").toString();
        String password = requestBody.get("password").toString();

        UserProfile user = userRepository.findByUsername(userName);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        else {
            return null;
        }


    }

}

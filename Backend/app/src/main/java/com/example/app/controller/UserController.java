package com.example.app.controller;

import com.example.app.model.AdvisorUser;
import com.example.app.model.StudentUser;
import com.example.app.model.Users;
import java.util.List;

import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    public UserController() {
    }

    @GetMapping({"users/all"})
    List<Users> GetAllUsers() {
        return this.userRepository.findAll();
    }

    @PostMapping({"users/post"})
    Users PostUserByBody(@RequestBody Users newUser) {
        this.userRepository.save(newUser);
        return newUser;
    }
}
package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping({"users/all"})
    List<User> GetAllUsers() {
        return this.userRepository.findAll();
    }

    @PostMapping({"users/post"})
    User PostUserByBody(@RequestBody User newUser) {
        this.userRepository.save(newUser);
        return newUser;
    }


}

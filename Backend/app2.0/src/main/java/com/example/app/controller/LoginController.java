package com.example.app.controller;

import com.example.app.model.Users;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class LoginController {


    @Autowired
    UserRepository UserRepo;

    @GetMapping("/users/{username}")
    public List<Users> getStudent(@PathVariable("username") String username) {
        return UserRepo.findAllByUsername(username);
    }

    @PostMapping("/login")
    public Users getUser(@RequestBody Map<String, Object> requestBody) {

        String userName = requestBody.get("username").toString();
        String password = requestBody.get("password").toString();

        Users user = UserRepo.findByUsername(userName);
        // check if user exists / password is correct
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        else {
            return null;
        }


    }


}

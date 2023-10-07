package com.example.app.controller;

import com.example.app.model.Users;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {


    @Autowired
    UserRepository UserRepo;

    @PostMapping("/login")
    public Users getUser(@RequestBody Map<String, Object> requestBody) {

        String userName = requestBody.get("username").toString();
        String password = requestBody.get("password").toString();

        Users user = UserRepo.findByUsername(userName);



        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        else {
            return null;
        }

    }


}

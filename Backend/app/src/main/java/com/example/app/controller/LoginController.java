package com.example.app.controller;

import com.example.app.model.UserProfile;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * LoginController class manages HTTP requests for user authentication.
 * It interacts with UserRepository to perform operations related to user login.
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    /**
     * Retrieves a list of UserProfile entities by their username.
     *
     * @param username the username of the UserProfile entities to retrieve
     * @return a list of UserProfile entities with the specified username
     */
    @GetMapping("/users/{username}")
    public List<UserProfile> getStudent(@PathVariable("username") String username) {
        return userRepository.findAllByUsername(username);
    }

    /**
     * Authenticates a user based on username and password provided in the request body.
     * Returns the UserProfile entity if authentication is successful, null otherwise.
     *
     * @param requestBody a Map containing the username and password for authentication
     * @return the authenticated UserProfile entity or null if authentication fails
     */
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

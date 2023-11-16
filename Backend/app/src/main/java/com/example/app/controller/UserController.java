package com.example.app.controller;

import com.example.app.model.Schedule;
import com.example.app.model.UserProfile;
import com.example.app.repository.ScheduleRepository;
import com.example.app.repository.UserRepository;
import com.example.app.miscellaneous.UsernameAndId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * UserController class manages HTTP requests for User resources.
 * It interacts with UserRepository and ScheduleRepository to perform CRUD operations on UserProfile entities and related operations.
 */
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    /**
     * Retrieves a UserProfile entity by its username.
     *
     * @param username the username of the UserProfile to retrieve
     * @return the UserProfile entity with the specified username
     */
    @GetMapping({"users/searchByName/{username}"})
    public UserProfile getUserByName(@PathVariable("username") String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Retrieves a UserProfile entity by its ID.
     *
     * @param user_id the ID of the UserProfile to retrieve
     * @return an Optional containing the UserProfile entity if found, or an empty Optional if not found
     */
    @GetMapping("/users/{user_id}")
    public Optional<UserProfile> getStudent(@PathVariable("user_id") Long user_id) {
        return userRepository.findById(user_id);
    }

    /**
     * Retrieves all UserProfile entities in the database.
     *
     * @return a list of all UserProfile entities
     */
    @GetMapping({"users/all"})
    List<UserProfile> GetAllUsers() {
        return this.userRepository.findAll();
    }

    /**
     * Retrieves all UserProfile entities in the database.
     *
     * @return a list of all UserProfile entities
     */
    @GetMapping("/userprofiles")
    public List<UserProfile> getAllUserProfiles() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a UserProfile entity by its user ID.
     *
     * @param userid the user ID of the UserProfile to retrieve
     * @return an Optional containing the UserProfile entity if found, or an empty Optional if not found
     */
    @GetMapping("/userprofile/{userid}")
    public Optional<UserProfile> getUserProfile(@PathVariable("userid") Long userid) {
        return userRepository.findById(userid);
    }

    /**
     * Retrieves a list of usernames and their corresponding IDs.
     *
     * @return a list of UsernameAndId objects containing usernames and their IDs
     */
    @GetMapping("/userprofiles/names")
    public List<UsernameAndId> getNames() {
        return userRepository.findAllByOrderByUsername();
    }

    /**
     * Retrieves a list of UserProfile entities that start with a specified search string.
     *
     * @param search the search string to filter usernames
     * @return a list of UserProfile entities matching the search criteria
     */
    @GetMapping("/userprofiles/{search}")
    public List<UserProfile> getSearched(@PathVariable("search") String search) {
        return userRepository.findByUsernameStartingWithOrderByUsername(search);
    }

    /**
     * Adds a new UserProfile entity to the database and creates an associated empty Schedule entity.
     *
     * @param newUser the new UserProfile entity to be added
     * @return the added UserProfile entity
     */
    @PostMapping({"users/post"})
    UserProfile PostUserByBody(@RequestBody UserProfile newUser) {
        this.userRepository.save(newUser);
        Schedule emptySchedule = new Schedule();
        emptySchedule.setUserProfile(newUser);
        scheduleRepository.save(emptySchedule);
        return newUser;
    }


}

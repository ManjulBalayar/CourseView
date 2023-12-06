package com.example.app.controller;

import com.example.app.model.Course;
import com.example.app.model.Schedule;
import com.example.app.model.UserProfile;
import com.example.app.repository.CourseRepository;
import com.example.app.repository.ScheduleRepository;
import com.example.app.repository.UserRepository;
import com.example.app.miscellaneous.UsernameAndId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.List;
import java.util.Optional;

/**
 * UserController class manages HTTP requests for User resources.
 * It interacts with UserRepository and ScheduleRepository to perform CRUD operations on UserProfile entities and related operations.
 */
@RestController
//@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    private CourseRepository courseRepository;

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

    @GetMapping("/users/{user_id}/friends")
    public ResponseEntity<List<UserProfile>> getUserFriends(@PathVariable("user_id") Long userId) {
        Optional<UserProfile> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            UserProfile user = userOptional.get();
            List<UserProfile> friends = user.getFriends();

            // You may want to exclude certain fields to prevent circular reference issues
            friends.forEach(friend -> friend.setFriends(null));

            return ResponseEntity.ok(friends);
        } else {
            return ResponseEntity.notFound().build();
        }
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

    @GetMapping({"/byRoles/{role}"})
    public List<UserProfile> getUserByRole(@PathVariable("role") String role) {
        return userRepository.findByRole(role);
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


    @DeleteMapping("/courses/delete/{courseId}/{username}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId, @PathVariable String username) {
        UserProfile user = userRepository.findByUsername(username);
        if (user != null && "Admin".equals(user.getRole())) {
            if (courseRepository.existsById(courseId)) {
                courseRepository.deleteById(courseId);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to delete course");
    }

    @PutMapping("/courses/update/{courseId}/{userId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId, @PathVariable Long userId, @RequestBody Course updatedCourse) {
        Optional<UserProfile> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            UserProfile user = userOptional.get();
            if ("Admin".equals(user.getRole())) {
                Optional<Course> courseOptional = courseRepository.findById(courseId);
                if (courseOptional.isPresent()) {
                    Course course = courseOptional.get();
                    course.setName(updatedCourse.getName());
                    course.setDescription(updatedCourse.getDescription());
                    course.setDepartment(updatedCourse.getDepartment());
                    courseRepository.save(course);

                    return ResponseEntity.ok().body("Course updated successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to update course");
    }

    @PostMapping("/users/addFriend/{userId}/{friendId}")
    public ResponseEntity<?> addFriend(@PathVariable Long userId, @PathVariable Long friendId) {

        Optional<UserProfile> userOptional = userRepository.findById(userId);
        Optional<UserProfile> friendOptional = userRepository.findById(friendId);
        if (userOptional.isPresent() && friendOptional.isPresent()) {
            UserProfile user = userOptional.get();
            UserProfile friend = friendOptional.get();

            user.getFriends().add(friend);
            friend.getFriends().add(user);

            userRepository.save(user);
            userRepository.save(friend);

            return ResponseEntity.ok().body("Friendship updated successfully");
        }


        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to do");
    }


}

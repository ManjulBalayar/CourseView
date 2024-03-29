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

    @GetMapping({"users/searchByName/{username}"})
    public UserProfile getUserByName(@PathVariable("username") String username) {
        return userRepository.findByUsername(username);
    }

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

    @GetMapping("/userprofiles")
    public List<UserProfile> getAllUserProfiles() {
        return userRepository.findAll();
    }

    @GetMapping("/userprofile/{userid}")
    public Optional<UserProfile> getUserProfile(@PathVariable("userid") Long userid) {
        return userRepository.findById(userid);
    }

    @GetMapping("/userprofiles/names")
    public List<UsernameAndId> getNames() {
        return userRepository.findAllByOrderByUsername();
    }

    @GetMapping("/userprofiles/{search}")
    public List<UserProfile> getSearched(@PathVariable("search") String search) {
        return userRepository.findByUsernameStartingWithOrderByUsername(search);
    }

    @GetMapping({"/byRoles/{role}"})
    public List<UserProfile> getUserByRole(@PathVariable("role") String role) {
        return userRepository.findByRole(role);
    }

    @PostMapping({"users/post"})
    UserProfile PostUserByBody(@RequestBody UserProfile newUser) {
        this.userRepository.save(newUser);
        Schedule emptySchedule = new Schedule();
        emptySchedule.setUserProfile(newUser);
        scheduleRepository.save(emptySchedule);
        return newUser;
    }

    @DeleteMapping("/courses/delete/{courseId}/{userId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId, @PathVariable Long userId) {
        UserProfile user = userRepository.findById(userId).orElse(null);
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

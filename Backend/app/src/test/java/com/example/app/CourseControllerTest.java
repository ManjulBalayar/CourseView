package com.example.app;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.example.app.controller.CourseController;
import com.example.app.model.Course;
import com.example.app.model.Schedule;
import com.example.app.repository.CourseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.app.model.UserProfile;
import com.example.app.repository.UserRepository;
import com.example.app.controller.LoginController;
import static org.hamcrest.Matchers.is;

import java.util.*;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRepository userRepository;

    @Test
    public void returnsUsernameTest() throws Exception {
        String name = "testName";
        Course course = new Course();
        course.setName(name);

        when(userRepository.findByNameStartingWithOrderByName(name)).thenReturn(Arrays.asList(course));

        mockMvc.perform(get("/courses/{search}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(name)));
    }

    @Test
    public void returnCourseID() throws Exception {
        String name = "testName";
        Course course = new Course();
        course.setCourseid(1L);
        course.setName(name);

        when(userRepository.findByNameStartingWithOrderByName(name)).thenReturn(Arrays.asList(course));

        mockMvc.perform(get("/course/{course_id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void returnCourses() throws Exception {
        String name = "testName";
        Course course = new Course();
        course.setCourseid(1L);
        course.setName(name);

        when(userRepository.findByNameStartingWithOrderByName(name)).thenReturn(Arrays.asList(course));

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCoursePost() throws Exception {
        String name = "testName";
        Course course = new Course();
        course.setCourseid(1L);
        course.setName(name);

        ObjectMapper objectMapper = new ObjectMapper();
        String addCourseJson = objectMapper.writeValueAsString(course);

        // Perform the POST request
        mockMvc.perform(post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addCourseJson)) // Convert object to JSON string
                .andExpect(status().isOk());
    }




}

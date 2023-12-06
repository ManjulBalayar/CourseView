package com.example.app;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.example.app.controller.CourseController;
import com.example.app.controller.ReviewController;
import com.example.app.controller.ScheduleController;
import com.example.app.miscellaneous.AddCourse;
import com.example.app.model.Course;
import com.example.app.model.Review;
import com.example.app.model.Schedule;
import com.example.app.repository.CourseRepository;
import com.example.app.repository.ReviewRepository;
import com.example.app.repository.ScheduleRepository;
import com.example.app.service.ScheduleCourseService;
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

@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @MockBean
    private ScheduleCourseService scheduleCourseService;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private ReviewRepository reviewRepository;

    @Test
    public void returnReviews() throws Exception {
        Schedule schedule = new Schedule();
        schedule.setScheduleid(1L);


        mockMvc.perform(get("/schedules"))
                .andExpect(status().isOk());
    }

    @Test
    public void returnSchedules() throws Exception {
        Schedule schedule = new Schedule();
        schedule.setScheduleid(1L);


        mockMvc.perform(get("/schedules"))
                .andExpect(status().isOk());
    }

    @Test
    public void returnSchedule() throws Exception {
        Schedule schedule = new Schedule();
        schedule.setScheduleid(1L);


        // Example using Mockito
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));


        mockMvc.perform(get("/schedule/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddLike() throws Exception {
        // Create a sample AddCourse object
        AddCourse addCourse = new AddCourse();
        addCourse.setUser_id(1L);
        addCourse.setCourse_id(1L);

        ObjectMapper objectMapper = new ObjectMapper();
        String addCourseJson = objectMapper.writeValueAsString(addCourse);


        // Perform the POST request
        mockMvc.perform(post("/addCourse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addCourseJson)) // Convert object to JSON string
                .andExpect(status().isOk());

        // Verify that the addStudentLikesCourse method is called with the correct arguments
        verify(scheduleCourseService, times(1)).addStudentLikesCourse(1L, 1L);
    }

    @Test
    public void testSchedulePost() throws Exception {
        // Create a sample AddCourse object
        Schedule schedule = new Schedule();
        schedule.setScheduleid(1L);
        schedule.setCourses(null);
        schedule.setUserProfile(null);
        schedule.getUserProfile();

        ObjectMapper objectMapper = new ObjectMapper();
        String addCourseJson = objectMapper.writeValueAsString(schedule);


        // Perform the POST request
        mockMvc.perform(post("/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addCourseJson)) // Convert object to JSON string
                .andExpect(status().isOk());

        // Verify that the addStudentLikesCourse method is called with the correct arguments
        //verify(scheduleCourseService, times(1)).addStudentLikesCourse(1L, 1L);
    }


}

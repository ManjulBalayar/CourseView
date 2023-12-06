package com.example.app;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.example.app.controller.*;
import com.example.app.miscellaneous.AddCourse;
import com.example.app.miscellaneous.Workload;
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

import static org.hamcrest.Matchers.is;

import java.util.*;

@WebMvcTest(WorkloadController.class)
public class WorkloadControllerTest {

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
    public void returnWorkload() throws Exception {
        Schedule schedule = new Schedule();
        schedule.setScheduleid(1L);
        Course course = new Course();
        course.setCourseid(1L);
        UserProfile userProfile = new UserProfile();
        userProfile.setUser_id(1L);


        // Example using Mockito
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        scheduleCourseService.addStudentLikesCourse(1L,1L);


        mockMvc.perform(get("/workload/byCourseID/{course_id}",1L))
                .andExpect(status().isOk());
    }

    @Test
    public void returnWorkloadSchedule() throws Exception {
        Schedule schedule = new Schedule();
        schedule.setScheduleid(1L);
        Course course = new Course();
        course.setCourseid(1L);
        UserProfile userProfile = new UserProfile();
        userProfile.setUser_id(1L);

        ArrayList<Course> courses = new ArrayList<Course>();
        courses.add(course);
        schedule.setCourses(courses);


        // Example using Mockito
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        scheduleCourseService.addStudentLikesCourse(1L,1L);


        mockMvc.perform(get("/workload/byUserid/{user_id}",1L))
                .andExpect(status().isOk());
    }

}

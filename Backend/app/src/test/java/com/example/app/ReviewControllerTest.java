package com.example.app;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.example.app.controller.CourseController;
import com.example.app.controller.ReviewController;
import com.example.app.model.Course;
import com.example.app.model.Review;
import com.example.app.model.Schedule;
import com.example.app.repository.CourseRepository;
import com.example.app.repository.ReviewRepository;
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

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewRepository userRepository;

    @Test
    public void returnReviews() throws Exception {
        Review review = new Review();
        review.setReview_id(1L);
        review.setCourseId(1L);
        review.setComment("test");
        review.setDifficulty(3L);
        review.setRating(3L);
        review.setTime_commitment(3L);


        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk());
    }

    @Test
    public void returnReviewID() throws Exception {
        Review review = new Review();
        review.setReview_id(1L);
        review.setCourseId(1L);
        review.setComment("test");
        review.setDifficulty(3L);
        review.setRating(3L);
        review.setTime_commitment(3L);


        mockMvc.perform(get("/review/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void returnReviewByUser() throws Exception {
        Review review = new Review();
        review.setReview_id(1L);
        review.setCourseId(1L);
        review.setUserId(1L);
        review.setComment("test");
        review.setDifficulty(3L);
        review.setRating(3L);
        review.setTime_commitment(3L);


        mockMvc.perform(get("/reviews/byUser/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testReviewPost() throws Exception {
        Review review = new Review();
        review.setReview_id(1L);
        review.setCourseId(1L);
        review.setUserId(1L);
        review.setComment("test");
        review.setDifficulty(3L);
        review.setRating(3L);
        review.setTime_commitment(3L);

        ObjectMapper objectMapper = new ObjectMapper();
        String addCourseJson = objectMapper.writeValueAsString(review);


        // Perform the POST request
        mockMvc.perform(post("/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addCourseJson)) // Convert object to JSON string
                .andExpect(status().isOk());
    }


}

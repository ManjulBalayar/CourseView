package com.example.app;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.app.model.UserProfile;
import com.example.app.repository.UserRepository;
import com.example.app.controller.LoginController;
import static org.hamcrest.Matchers.is;

import java.util.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void returnsUsernameTest() throws Exception {
        String testUsername = "testUser";
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(testUsername);

        when(userRepository.findAllByUsername(testUsername)).thenReturn(Arrays.asList(userProfile));

        mockMvc.perform(get("/users/{username}", testUsername))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username", is(testUsername)));
    }

    @Test
    public void testUserLogin() throws Exception {
        String testUsername = "testUser";
        String testPassword = "testPass";
        UserProfile testUser = new UserProfile();
        testUser.setUsername(testUsername);
        testUser.setPassword(testPassword);

        when(userRepository.findByUsername(testUsername)).thenReturn(testUser);

        // Construct request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", testUsername);
        requestBody.put("password", testPassword);
        String jsonRequestBody = new ObjectMapper().writeValueAsString(requestBody);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(testUsername)));
    }

}

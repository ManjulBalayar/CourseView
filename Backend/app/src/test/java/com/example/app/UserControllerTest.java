package com.example.app;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.example.app.model.Schedule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.app.model.UserProfile;
import com.example.app.repository.*;
import com.example.app.controller.UserController;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @Test
    public void getAllUserProfilesTest() throws Exception {
        UserProfile userProfile1 = new UserProfile();
        userProfile1.setUsername("user1");
        UserProfile userProfile2 = new UserProfile();
        userProfile2.setUsername("user2");

        List<UserProfile> allUserProfiles = Arrays.asList(userProfile1, userProfile2);
        when(userRepository.findAll()).thenReturn(allUserProfiles);

        mockMvc.perform(get("/userprofiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("user1")))
                .andExpect(jsonPath("$[1].username", is("user2")));
    }

    @Test
    public void testPostUserByBody() throws Exception {
        UserProfile newUser = new UserProfile();
        newUser.setUsername("newUser");
        newUser.setPassword("newPass");

        String jsonRequestBody = new ObjectMapper().writeValueAsString(newUser);

        mockMvc.perform(post("/users/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("newUser")));

        verify(userRepository, times(1)).save(any(UserProfile.class));
        verify(scheduleRepository, times(1)).save(any(Schedule.class));
    }
}

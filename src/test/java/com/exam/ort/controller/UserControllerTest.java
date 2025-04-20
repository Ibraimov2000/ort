package com.exam.ort.controller;

import com.exam.ort.config.SecurityConfig;
import com.exam.ort.enums.Role;
import com.exam.ort.model.UserRecord;
import com.exam.ort.service.StatisticsService;
import com.exam.ort.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private AuthenticationProvider authenticationProvider;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private StatisticsService statisticsService;

    @Test
    void getUserById() throws Exception {
        UserRecord userRecord = new UserRecord(1L, "John", "Doe", "john.doe@example.com", Role.USER, "password");

        when(userService.findById(1L)).thenReturn(userRecord);

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }
}

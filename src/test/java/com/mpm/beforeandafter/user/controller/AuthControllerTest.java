package com.mpm.beforeandafter.user.controller;

import com.mpm.beforeandafter.role.type.RoleType;
import com.mpm.beforeandafter.user.dto.CreateUserRequestDto;
import com.mpm.beforeandafter.user.dto.CreateUserResponseDto;
import com.mpm.beforeandafter.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        // Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Should create and return the user")
    void createUserShouldCreateUserAndReturnCreated() throws Exception {
        String userJson = "{\"userName\":\"John\",\"userEmail\":\"john@example.com\",\"userPassword\":\"123456789\"}";
        CreateUserResponseDto responseDto =  CreateUserResponseDto.builder()
                .userName("John")
                .email("john@example.com")
                .roles(List.of(RoleType.ROLE_USER.name()))
                .build();

        when(userService.createUser(any(CreateUserRequestDto.class), eq(RoleType.ROLE_USER))).thenReturn(responseDto);

        String signupAuth = "http://localhost:8080/api/v1/auth/signup";
        mockMvc.perform(post(signupAuth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated());

    }

//    @Test
//    @DisplayName("Should create and return the user")
//    void authenticateUserShouldCreateUserAndReturnCreated() throws Exception {
//
//    }
}
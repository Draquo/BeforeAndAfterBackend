package com.mpm.beforeandafter.user.controller;
import com.mpm.beforeandafter.role.model.Role;
import com.mpm.beforeandafter.role.type.RoleType;
import com.mpm.beforeandafter.user.dto.GetUserResponseDto;
import com.mpm.beforeandafter.user.model.User;
import com.mpm.beforeandafter.user.service.UserMapper;
import com.mpm.beforeandafter.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    private UserMapper userMapper;

@Autowired
private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Should return all users")
    public void getAllUsersShouldReturnUsers() throws Exception {
        // GIVEN
        RoleType userRoleType = RoleType.ROLE_USER;
        Role userRole = new Role(userRoleType);
        User user1 = new User();
        user1.setName("Name1");
        user1.setEmail("Email1");
        user1.setRoles(Set.of(userRole));
        User user2 = new User();
        user2.setName("Name2");
        user2.setEmail("Email2");
        user2.setRoles(Set.of(userRole));
        GetUserResponseDto user1Dto = userMapper.mapToGetUserResponseDto(user1);
        GetUserResponseDto user2Dto = userMapper.mapToGetUserResponseDto(user2);

        // WHEN
        when(userService.getUsers(userRoleType)).thenReturn(List.of(user1Dto, user2Dto));

        // THEN
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("roleType", "ROLE_USER");
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users")
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("[{},{}]"));
    }

    @Test
    @DisplayName("Should return user by indicated id")
    public void getUserByIdShouldReturnUserByIndicatedId() throws Exception {
        // GIVEN
        RoleType userRoleType = RoleType.ROLE_USER;
        Role userRole = new Role(userRoleType);
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Name1");
        user1.setEmail("Email1");
        user1.setRoles(Set.of(userRole));
        GetUserResponseDto user1Dto = userMapper.mapToGetUserResponseDto(user1);

        // WHEN
        when(userService.getUserById(1L)).thenReturn(user1Dto);

        // THEN
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("roleType", "ROLE_USER");
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/1")
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName", is("Name1")))
                .andExpect(jsonPath("$.email", is("Email1")));
    }
}
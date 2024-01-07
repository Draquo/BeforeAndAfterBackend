package com.mpm.beforeandafter.user.controller;

import com.mpm.beforeandafter.role.type.RoleType;
import com.mpm.beforeandafter.user.dto.CreateUserRequestDto;
import com.mpm.beforeandafter.user.dto.CreateUserResponseDto;
import com.mpm.beforeandafter.user.dto.SignInResponseDto;
import com.mpm.beforeandafter.user.dto.SignInRequestDto;
import com.mpm.beforeandafter.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponseDto createUser(@RequestBody CreateUserRequestDto user) {
        //TODO: delete sout when security developed
        System.out.println("CreateUserRequestDto = " + user );
        return userService.createUser(user, RoleType.ROLE_USER);
    }

    @PostMapping("/signin")
    public SignInResponseDto authenticateUser(@RequestBody SignInRequestDto user) {
        Authentication authentication = userService.getAuthentication(user);
        String jwt = userService.getSecurityContextAndJwt(user, authentication);
        User userDetails = userService.getUserDetails(authentication);
        List<String> roles = userService.getRoles(userDetails);
        return userService.createJwtResponse(jwt, userDetails.getUsername(), roles);
    }
}

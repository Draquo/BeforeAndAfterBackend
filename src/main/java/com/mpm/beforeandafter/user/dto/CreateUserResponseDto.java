package com.mpm.beforeandafter.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateUserResponseDto {

    private String userName;
    private String email;
    private List<String> roles;
}
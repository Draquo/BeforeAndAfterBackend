package com.mpm.beforeandafter.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInResponseDto {

    private String jwt;
    private String email;
    private List<String> roles;
}

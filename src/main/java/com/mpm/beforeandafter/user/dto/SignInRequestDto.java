package com.mpm.beforeandafter.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SignInRequestDto {

    private String userEmail;
    private String userPassword;
}

package com.ramdan.trainingkaryawan.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginRequest {
    @NotBlank(message = "username is required.")
    private String username;
    @NotBlank(message = "password is required.")
    private String password;
}

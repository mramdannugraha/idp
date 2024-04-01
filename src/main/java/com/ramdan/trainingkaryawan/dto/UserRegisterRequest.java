package com.ramdan.trainingkaryawan.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterRequest {
    @Email(message = "email tidak valid. masukkan email yang valid")
    @NotBlank(message = "username is required.")
    private String username;
    @NotBlank(message = "password is required.")
    private String password;
    @NotBlank(message = "fullname is required.")
    private String fullname;

    private String name;

    private String phone_number;

    private String domicile;

    private String gender;
}

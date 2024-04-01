package com.ramdan.trainingkaryawan.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class UserResetPasswordRequest {
    @NotBlank(message = "email is required.")
    public String email;
    @NotBlank(message = "OTP is required.")
    public String otp;
    @NotBlank(message = "newPassword is required.")
    public String newPassword;
    @NotBlank(message = "confirmNewPassword is required.")
    public String confirmNewPassword;
}


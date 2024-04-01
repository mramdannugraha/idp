package com.ramdan.trainingkaryawan.service;

import com.ramdan.trainingkaryawan.dto.UserLoginRequest;
import com.ramdan.trainingkaryawan.dto.UserRegisterRequest;

import java.security.Principal;
import java.util.Map;

public interface UserService {
    Map login(UserLoginRequest objLogin);
    Map registerManual(UserRegisterRequest objModel) ;
    Map getDetailProfile(Principal principal);
    void sendEmailRegister(UserRegisterRequest user, boolean tymeleaf);
}

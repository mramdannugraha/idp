package com.ramdan.trainingkaryawan.controller;

import com.ramdan.trainingkaryawan.dto.UserRegisterRequest;
import com.ramdan.trainingkaryawan.model.oauth.User;
import com.ramdan.trainingkaryawan.repository.oauth.UserRepository;
import com.ramdan.trainingkaryawan.service.UserService;
import com.ramdan.trainingkaryawan.service.email.EmailSender;
import com.ramdan.trainingkaryawan.utils.Config;
import com.ramdan.trainingkaryawan.utils.EmailTemplate;
import com.ramdan.trainingkaryawan.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/user-register")
public class RegisterController {
    @Autowired
    private UserRepository userRepository;

    Config config = new Config();

    @Autowired
    public UserService userService;

    @Autowired
    public Response templateCRUD;

    @Autowired
    public EmailSender emailSender;

    @Autowired
    public EmailTemplate emailTemplate;

    @Value("${expired.token.password.minute}")
    int expiredToken;

    @Value("${BASEURL:}")
    private String BASEURL;


    @PostMapping("/")
    public ResponseEntity<Map> saveRegisterManual(@Valid @RequestBody UserRegisterRequest objModel) throws RuntimeException {
        Map map = userService.registerManual(objModel);

        Map sendOTPUri = sendEmailRegisterTymeleaf(objModel); // step 2.A
//        Map sendOTP = sendEmailRegister(objModel); // step 2.B
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    // Step 2.A: send OTP (URL activation): update enable agar dapat login
    @PostMapping("/send-otp-tymeleaf")
    public Map sendEmailRegisterTymeleaf(@RequestBody UserRegisterRequest user) {
        String message = "Thanks, please check your email for activation.";
        boolean tymeleaf = true;
        userService.sendEmailRegister(user, tymeleaf);
        return templateCRUD.templateSukses(message);
    }

    // Step 2.B: send OTP: update enable agar dapat login
    @PostMapping("/send-otp")
    public Map sendEmailRegister(@RequestBody UserRegisterRequest user) {
        String message = "Thanks, please check your email for activation.";
        boolean tymeleaf = false;
        userService.sendEmailRegister(user, tymeleaf);
        return templateCRUD.templateSukses(message);
    }

    //Step 3:
    @GetMapping("/register-confirm-otp/{token}")
    public ResponseEntity<Map> saveRegisterManual(@PathVariable(value = "token") String tokenOtp,
                                                  @RequestBody UserRegisterRequest model) throws RuntimeException {
        User user = userRepository.findOneByUsernameAndOTP(model.getUsername(), tokenOtp);
//        User user = userRepository.findOneByOTP(tokenOtp);
        if (null == user) {
            return new ResponseEntity<Map>(templateCRUD.templateEror("Username & OTP tidak ditemukan"), HttpStatus.OK);
        }

        if(user.isEnabled()){
            return new ResponseEntity<Map>(templateCRUD.templateSukses("Akun Anda sudah aktif, Silahkan melakukan login"), HttpStatus.OK);
        }
        String today = config.convertDateToString(new Date());

        String dateToken = config.convertDateToString(user.getOtpExpiredDate());
        if(Long.parseLong(today) > Long.parseLong(dateToken)){
            return new ResponseEntity<Map>(templateCRUD.templateEror("Your token is expired. Please Get token again."), HttpStatus.OK);
        }
        //update user
        user.setEnabled(true);
        userRepository.save(user);

        return new ResponseEntity<Map>(templateCRUD.templateSukses("Sukses, Silahkan Melakukan Login"), HttpStatus.OK);
    }



}

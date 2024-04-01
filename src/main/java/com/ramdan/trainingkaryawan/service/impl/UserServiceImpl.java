package com.ramdan.trainingkaryawan.service.impl;

import com.ramdan.trainingkaryawan.dto.UserLoginRequest;
import com.ramdan.trainingkaryawan.dto.UserRegisterRequest;
import com.ramdan.trainingkaryawan.model.oauth.Role;
import com.ramdan.trainingkaryawan.model.oauth.User;
import com.ramdan.trainingkaryawan.repository.oauth.RoleRepository;
import com.ramdan.trainingkaryawan.repository.oauth.UserRepository;
import com.ramdan.trainingkaryawan.service.UserService;
import com.ramdan.trainingkaryawan.service.ValidationService;
import com.ramdan.trainingkaryawan.service.email.EmailSender;
import com.ramdan.trainingkaryawan.service.oauth.Oauth2UserDetailsService;
import com.ramdan.trainingkaryawan.utils.EmailTemplate;
import com.ramdan.trainingkaryawan.utils.Response;
import com.ramdan.trainingkaryawan.utils.SimpleStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Value("${BASEURL}")
    private String BASEURL;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    public Response templateResponse;
    @Autowired
    private Oauth2UserDetailsService userDetailsService;
    @Autowired
    private ValidationService validationService;
    @Autowired
    Response response;
    @Autowired
    public EmailSender emailSender;
    @Autowired
    public EmailTemplate emailTemplate;
    @Value("${expired.token.password.minute}")
    int expiredToken;

    @Override
    public Map login(UserLoginRequest loginRequest) {

        try {
            Map<String, Object> map = new HashMap<>();

            User checkUser = userRepository.findOneByUsername(loginRequest.getUsername());

            if ((checkUser != null) && (encoder.matches(loginRequest.getPassword(), checkUser.getPassword()))) {
                if (!checkUser.isEnabled()) {
                    map.put("is_enabled", checkUser.isEnabled());
                    return templateResponse.templateEror(map);
                }
            }
            if (checkUser == null) {
                return templateResponse.notFound("user not found");
            }
            if (!(encoder.matches(loginRequest.getPassword(), checkUser.getPassword()))) {
                return templateResponse.templateEror("wrong password");
            }
            String url = BASEURL + "/oauth/token?username=" + loginRequest.getUsername() +
                    "&password=" + loginRequest.getPassword() +
                    "&grant_type=password" +
                    "&client_id=my-client-web" +
                    "&client_secret=password";

            ResponseEntity<Map> response = restTemplateBuilder.build().exchange(url, HttpMethod.POST, null,
                    new ParameterizedTypeReference<Map>() {});

            if (response.getStatusCode() == HttpStatus.OK) {
                User user = userRepository.findOneByUsername(loginRequest.getUsername());
                List<String> roles = new ArrayList<>();

                for (Role role : user.getRoles()) {
                    roles.add(role.getName());
                }
                //save token
//                checkUser.setAccessToken(response.getBody().get("access_token").toString());
//                checkUser.setRefreshToken(response.getBody().get("refresh_token").toString());
//                userRepository.save(checkUser);

                map.put("access_token", response.getBody().get("access_token"));
                map.put("token_type", response.getBody().get("token_type"));
                map.put("refresh_token", response.getBody().get("refresh_token"));
                map.put("expires_in", response.getBody().get("expires_in"));
                map.put("scope", response.getBody().get("scope"));
                map.put("jti", response.getBody().get("jti"));

                return map;
            } else {
                return templateResponse.notFound("user not found");
            }
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return templateResponse.templateEror("invalid login");
            }
            return templateResponse.templateEror(e);
        } catch (Exception e) {
            e.printStackTrace();

            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map registerManual(UserRegisterRequest objModel) {
        validationService.validate(objModel);

        User existingUser = userRepository.checkExistingEmail(objModel.getUsername());
        if (existingUser != null) {
            throw new ResponseStatusException(HttpStatus.IM_USED, "Username sudah terdaftar");
        }

        try {
            String[] roleNames = {"ROLE_USER", "ROLE_READ", "ROLE_WRITE"}; // admin
            User user = new User();
            user.setUsername(objModel.getUsername().toLowerCase());
            user.setFullname(objModel.getFullname());

            user.setEnabled(false); // matikan user

            String password = encoder.encode(objModel.getPassword().replaceAll("\\s+", ""));
            List<Role> r = roleRepository.findByNameIn(roleNames);

            user.setRoles(r);
            user.setPassword(password);
            User obj = userRepository.save(user);

            return templateResponse.templateSukses(obj);

        } catch (Exception e) {
            logger.error("Error registerManual=", e);
            return templateResponse.templateEror("eror:"+e);
        }

    }

    @Override
    public void sendEmailRegister(UserRegisterRequest user, boolean tymeleaf) {
        validationService.validate(user);

        User found = userRepository.findOneByUsername(user.getUsername());
        if (found == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found");

        String template = emailTemplate.getRegisterTemplate();
        if (org.apache.commons.lang3.StringUtils.isEmpty(found.getOtp())) {
            User search;
            String otp;
            do {
                otp = SimpleStringUtils.randomString(6, true);
                search = userRepository.findOneByOTP(otp);
            } while (search != null);
            Date dateNow = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateNow);
            calendar.add(Calendar.MINUTE, expiredToken);
            Date expirationDate = calendar.getTime();

            found.setOtp(otp);
            found.setOtpExpiredDate(expirationDate);

            if (tymeleaf) {
                template = template.replaceAll("\\{\\{USERNAME}}", (found.getFullname() == null ? found.getUsername() : found.getFullname()));
                template = template.replaceAll("\\{\\{VERIFY_TOKEN}}", BASEURL + "/user-register/web/index/"+ otp);
            } else {
                template = template.replaceAll("\\{\\{USERNAME}}", (found.getFullname() == null ? found.getUsername() : found.getFullname()));
                template = template.replaceAll("\\{\\{VERIFY_TOKEN}}", otp);
            }
            userRepository.save(found);
        } else {
            if (tymeleaf) {
                template = template.replaceAll("\\{\\{USERNAME}}", (found.getFullname() == null ? found.getUsername() : found.getFullname()));
                template = template.replaceAll("\\{\\{VERIFY_TOKEN}}", BASEURL + "/user-register/web/index/"+  found.getOtp());
            } else {
                template = template.replaceAll("\\{\\{USERNAME}}", (found.getFullname() == null ? found.getUsername() : found.getFullname()));
                template = template.replaceAll("\\{\\{VERIFY_TOKEN}}", found.getOtp());
            }
        }
        emailSender.sendAsync(found.getUsername(), "Register", template);
    }

    @Override
    public Map getDetailProfile(Principal principal) {
        User idUser = getUserIdToken(principal, userDetailsService);
        try {
            User obj = userRepository.save(idUser);
            return response.sukses(obj);
        } catch (Exception e){
            return response.error(e,"500");
        }
    }

    private User getUserIdToken(Principal principal, Oauth2UserDetailsService userDetailsService) {
        UserDetails user = null;
        String username = principal.getName();
        if (!StringUtils.isEmpty(username)) {
            user = userDetailsService.loadUserByUsername(username);
        }

        if (null == user) {
            throw new UsernameNotFoundException("User not found");
        }
        User idUser = userRepository.findOneByUsername(user.getUsername());
        if (null == idUser) {
            throw new UsernameNotFoundException("User name not found");
        }
        return idUser;
    }


}

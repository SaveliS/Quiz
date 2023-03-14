package com.qiuiz.quizFinal.model.support;

import com.qiuiz.quizFinal.model.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Slf4j
public class RegistrationForm {
    private String username;
    private String password;
    private String email;
    public User toUser(PasswordEncoder passwordEncoder){
        log.info("username: {}", username);
        log.info("password: {}", password);
        password =  passwordEncoder.encode(password);
        log.info("password: {}",password);
        log.info("Password encoder: {}", passwordEncoder.encode(password));
        log.info("email");
        return new User(username,password,email);
    }
}

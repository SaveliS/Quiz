package com.qiuiz.quizFinal.model.support;

import com.qiuiz.quizFinal.model.Roles;
import com.qiuiz.quizFinal.model.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Data
@Slf4j
public class RegistrationForm {
    private String username;
    private String password;
    private String email;
    public User toUser(PasswordEncoder passwordEncoder) {
        log.info("password: {}", password);
        password =  passwordEncoder.encode(password);
        log.info("password: {}",password);
        log.info("Password encoder: {}", passwordEncoder.encode(password));
        log.info("email");
        return new User(username,password,email,new Roles("ROLE_USER"),null, null);
    }
}

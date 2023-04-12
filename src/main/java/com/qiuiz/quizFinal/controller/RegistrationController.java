package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.model.User;
import com.qiuiz.quizFinal.model.support.RegistrationForm;
import com.qiuiz.quizFinal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserRepository userRepo;
    private PasswordEncoder encoder;

    public RegistrationController(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @GetMapping()
    public String registerForm(Model model){
        model.addAttribute("reg", new User());
        log.info("Отдал страницу");
        return "aut/registration";
    }

    @PostMapping()
    public String processRegistration(RegistrationForm form) {
        log.info("Encoder: {}", encoder);
        log.info("Форма регистрации: {}", form.getEmail());
        log.info("Форма регистрации: {}", form.getUsername());
        log.info("Форма регистрации: {}", form.getPassword());
        userRepo.save(form.toUser(encoder));
        return "redirect:/login";
    }
}

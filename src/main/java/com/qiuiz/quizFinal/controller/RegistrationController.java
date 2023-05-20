package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.model.Token;
import com.qiuiz.quizFinal.model.User;
import com.qiuiz.quizFinal.model.support.RegistrationForm;
import com.qiuiz.quizFinal.repository.TokenRepository;
import com.qiuiz.quizFinal.repository.UserRepository;
import com.qiuiz.quizFinal.service.EmailService;
import com.qiuiz.quizFinal.service.TokenService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@Controller
public class RegistrationController {
    @Autowired
    private TokenRepository tokenRepository;
    private UserRepository userRepo;
    private PasswordEncoder encoder;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TokenService tokenService;

    public RegistrationController(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("reg", new User());
        model.addAttribute("emailError", false);
        return "aut/registration";
    }

    @PostMapping("/register")
    public String processRegistration(RegistrationForm form, Model model) throws MessagingException {
        if(userRepo.findByEmail(form.getEmail()) != null){
            model.addAttribute("reg", form);
            model.addAttribute("emailError", true);
            return "aut/registration";
        }
        userRepo.save(form.toUser(encoder));

        User currentUser = userRepo.findByEmail(form.getEmail());
        Token confirmationUrl = tokenService.generateToken(currentUser);
        tokenRepository.save(confirmationUrl);
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        emailService.sendEmail(userRepo.findByEmail(form.getEmail()).getEmail(), confirmationUrl.getToken(), baseUrl);
        model.addAttribute("email",currentUser.getEmail());
        return "activateProfile/activateUser";
    }
}

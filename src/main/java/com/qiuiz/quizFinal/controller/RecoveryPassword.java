package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.model.User;
import com.qiuiz.quizFinal.repository.TokenRepository;
import com.qiuiz.quizFinal.repository.UserRepository;
import com.qiuiz.quizFinal.service.EmailService;
import com.qiuiz.quizFinal.service.TokenService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@Controller
public class RecoveryPassword {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/recovery")
    public String SendRecoveryToken(Model model){
        return "recoveryPassword/sendEmailRecoveryPassword";
    }

    @PostMapping("/recovery")
    public String SendRecoveryEmail(@RequestParam(name = "email_input") String email, Model model) throws MessagingException {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        if(userRepository.findByEmail(email) == null){
            return "aut/login";
        }
        String token = tokenService.generateToken(userRepository.findByEmail(email)).getToken();
        emailService.sendRecoveryEmail(email,token,baseUrl);
        model.addAttribute("email", email);
        return "activateProfile/activateUser";
    }

    @GetMapping("/recovery/password")
    public String GetRecoveryPassword(@RequestParam("token") String token,
                                      @RequestParam("email") String email,Model model){
        if(userRepository.findByEmail(email) != null){
            User user = userRepository.findByEmail(email);
            log.info("Token user: {}", user.getToken().getToken());
            log.info("Token URL: {}",token);
            if(user.getToken().getToken() == tokenRepository.findByToken(token).getToken()){
                model.addAttribute("user", user);
                return "recoveryPassword/recoveryPassword";
            }
        }
        return "aut/login";
    }

    @PostMapping("/recovery/password")
    public String UpdatePassword(@RequestParam(name = "password") String password, final User user,
                                 @RequestParam(name = "confirm_password") String confirm_password,
                                 Model model){
        if(password.equals(confirm_password)){
            log.info("Равны");
            User userFind = userRepository.findById(user.getIduser()).get();
            userFind.setPassword(encoder.encode(password));
            userRepository.save(userFind);
        }
        return "aut/login";
    }
}

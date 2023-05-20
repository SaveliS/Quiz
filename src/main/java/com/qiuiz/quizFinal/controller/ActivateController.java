package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.model.Token;
import com.qiuiz.quizFinal.model.User;
import com.qiuiz.quizFinal.repository.TokenRepository;
import com.qiuiz.quizFinal.repository.UserRepository;
import com.qiuiz.quizFinal.service.EmailService;
import com.qiuiz.quizFinal.service.TokenService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class ActivateController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping("/activate")
    public String ActivateAccountUser(final HttpSession req, Model model) throws MessagingException {
        String userName = req.getAttribute("userName").toString();
        User user = userRepository.findByUsername(userName);
        log.info("User info: {}", user);
        Token token = tokenService.generateToken(user);
        emailService.sendEmail(user.getEmail(), token.getToken(), request.getRequestURL().toString());
        model.addAttribute("email",user.getEmail());
        return "activateProfile/activateUser";
    }

    @GetMapping("/confirm")
    public String ConfirmAccountUser(@RequestParam("token") String token , @RequestParam("email") String email, Model model){
        log.info("token: {}",token);
        log.info("email: {}",email);
        User user = userRepository.findByEmail(email);
        Token tokenInDB = tokenRepository.findByUser(userRepository.findByEmail(email));
        Token tokenExcepted = tokenRepository.findByToken(token);
        log.info("user: {}",user);
        log.info("tokenInDB: {}",tokenInDB);
        log.info("tokenExcepted: {}",tokenExcepted);
        if(user != null && tokenInDB != null){
            log.info("ID user: {}\n Token ID user: {}", user.getIduser(),tokenInDB.getUser().getIduser());
            log.info("Token find user: {} \nToken find token: {}",tokenInDB.getToken(),tokenExcepted.getToken());
            if(user.getIduser() == tokenInDB.getUser().getIduser()){
                if(tokenInDB.getToken() == tokenExcepted.getToken()){
                    log.info("Токены равны");
                    user.setEnable(true);
                    userRepository.save(user);
                    log.info("Прошло успешно!");
                }
            }
            log.info("Token in DN:"+ userRepository.findByEmail(email).getToken().getToken());
            log.info("Token in Query:" + token);
        }
        model.addAttribute("loginUser",userRepository.findByEmail(email).getUsename());
        model.addAttribute("idUser",userRepository.findByEmail(email));
        return "home";
    }
}

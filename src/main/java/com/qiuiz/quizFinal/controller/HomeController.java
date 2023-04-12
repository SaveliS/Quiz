package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.repository.QuizRepository;
import com.qiuiz.quizFinal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
public class HomeController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping()
    public String home(Model model){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loginUser", login);
        model.addAttribute("idUser",userRepository.findByUsername(login));
        log.info("Name login: {}", login);
        return "home";
    }
}

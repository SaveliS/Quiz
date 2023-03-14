package com.qiuiz.quizFinal.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class UserController {
    @GetMapping("/logout_user")
    public String leaveUser(HttpServletRequest request){
        log.info("Зашел в logout");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            request.getSession().invalidate();
            log.info("Уничтожил user");
        }
        return "redirect:/";
    }
}

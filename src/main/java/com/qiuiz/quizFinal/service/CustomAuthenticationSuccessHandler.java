package com.qiuiz.quizFinal.service;

import com.qiuiz.quizFinal.model.User;
import com.qiuiz.quizFinal.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final GrantedAuthority userAuthority = new SimpleGrantedAuthority("USER");

    @Autowired
    private UserRepository userRepository;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("Processing successful authentication for user {}", authentication.getName());
        User user = userRepository.findByUsername(authentication.getName());
        if(user != null){
            String targetUrl = "/";
            getRedirectStrategy().sendRedirect(request,response,targetUrl);
        }
        else{
            super.onAuthenticationSuccess(request,response,authentication);
        }
    }
}

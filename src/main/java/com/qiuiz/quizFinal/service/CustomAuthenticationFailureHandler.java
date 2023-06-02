package com.qiuiz.quizFinal.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException exception) throws ServletException, IOException {
        log.info("Ошибка: {}", exception.getClass().getName());
        if(exception instanceof DisabledException){
            log.info("Ошибка: Аккаунт выключен");
            String username = request.getParameter("username");
            log.info("Логин: {}",username);
            request.getSession().setAttribute("userName", username);
            setDefaultFailureUrl("/activate");
        }
        if(exception instanceof BadCredentialsException){
            log.info("Ошибка: Введены неверные данные");
            setDefaultFailureUrl("/login?error");
        }
        super.onAuthenticationFailure(request, response, exception);
    }
}

package com.qiuiz.quizFinal.service;

import com.qiuiz.quizFinal.model.User;
import com.qiuiz.quizFinal.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        super.onAuthenticationFailure(request, response, exception);
    }
}

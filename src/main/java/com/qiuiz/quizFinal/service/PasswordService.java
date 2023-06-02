package com.qiuiz.quizFinal.service;

import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    public boolean isValidPassword(String password) {
        // Проверка на корректность пароля
        if (password.length() < 8) {
            return false;
        }

        boolean hasDigit = false;
        boolean hasLetter = false;

        for (char ch : password.toCharArray()) {
            if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (Character.isLetter(ch)) {
                hasLetter = true;
            }
        }

        return hasDigit && hasLetter;
    }
}

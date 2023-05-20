package com.qiuiz.quizFinal.service;

import com.qiuiz.quizFinal.model.Token;
import com.qiuiz.quizFinal.model.User;
import com.qiuiz.quizFinal.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService {
    // Время действия токена.
    private static final int EXPIRATION_MINUTES = 30;

    @Autowired
    private TokenRepository tokenRepository;

    public Token generateToken(User user){
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES);
        // Если пользователь уже генерировал токен
        if(user.getToken() != null){
            Token userToken = tokenRepository.findById(user.getToken().getId_token()).get(); // Получаем токен из БД.
            //Устанавливаем значения для токена. Время действия и сам новый токен.
            userToken.setToken(token);
            userToken.setExpiryDate(expiryDate);
            tokenRepository.save(userToken);
            // Возвращаем обновленный токен.
            return userToken;
        }
        Token verificationToken = new Token(user,token,expiryDate);
        verificationToken.setUser(user);
        tokenRepository.save(verificationToken);
        user.setToken(verificationToken);
        // Возвращаем новый токен.
        return verificationToken;
    }
}

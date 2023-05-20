package com.qiuiz.quizFinal;

import com.qiuiz.quizFinal.model.Roles;
import com.qiuiz.quizFinal.model.Token;
import com.qiuiz.quizFinal.model.User;
import com.qiuiz.quizFinal.repository.TokenRepository;
import com.qiuiz.quizFinal.repository.UserRepository;
import com.qiuiz.quizFinal.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    // Проверка, если токен уже есть в БД, то обновляем.
    @Test
    public void testUpdateToken(){
        User user = userRepository.findById(9).get();
        Token notUpdatet = tokenRepository.findByUser(user);
        Token token = tokenService.generateToken(user);
        Token updated = tokenRepository.findByUser(user);
        assertNotEquals(notUpdatet.getToken(),updated.getToken());
    }

    @Test
    public void testTokenRepository(){

        User user = userRepository.findByUsername("test");

        Token token = tokenService.generateToken(user);
        tokenRepository.save(token);

        Token foundToken = tokenRepository.findByUser(user);
        assertEquals(token.getToken(),foundToken.getToken());
    }

    @Test
    public void testTokenCorrect(){
        String tokenInDB = "5a76b889-9912-4a9f-8dcb-1f5a10482133";
        Token token = tokenRepository.findById(2).get();
        System.out.println("Expected value: " + tokenInDB);
        System.out.println("Actual value: " + token.getToken());
        assertEquals(tokenInDB, token.getToken());
    }

    @Test
    public void testTokenSearch(){
        String tokenInDB = "5a76b889-9912-4a9f-8dcb-1f5a10482133";
        Token token = tokenRepository.findByToken(tokenInDB);
        System.out.println("Expected value: " + tokenInDB);
        System.out.println("Actual value: " + token.getToken());
        assertEquals(tokenInDB, token.getToken());
    }

    @Test
    public void testEqualsIf(){
        String tokenInDB = "5a76b889-9912-4a9f-8dcb-1f5a10482133";
        Token token = tokenRepository.findByToken(tokenInDB);
        if(Objects.equals(token.getToken(), tokenInDB)){
            assertEquals(1,1);
        }
        assertEquals(1,2);
    }
}

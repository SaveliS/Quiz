package com.qiuiz.quizFinal.repository;

import com.qiuiz.quizFinal.model.Token;
import com.qiuiz.quizFinal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByToken(String token);
    Token findByUser(User user);
}

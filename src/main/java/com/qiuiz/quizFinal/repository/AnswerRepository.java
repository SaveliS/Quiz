package com.qiuiz.quizFinal.repository;

import com.qiuiz.quizFinal.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {
}

package com.qiuiz.quizFinal.repository;

import com.qiuiz.quizFinal.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Integer> {
}

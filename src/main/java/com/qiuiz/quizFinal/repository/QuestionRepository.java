package com.qiuiz.quizFinal.repository;

import com.qiuiz.quizFinal.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
}

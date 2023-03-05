package com.qiuiz.quizFinal.repository;

import com.qiuiz.quizFinal.model.Answer;
import com.qiuiz.quizFinal.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
}

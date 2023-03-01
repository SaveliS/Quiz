package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("Quiz")
public class DesignQuiz {

    @GetMapping()
    public String showDesignForm(){
        return "design";
    }

    @PostMapping()
    public String processQuiz(@ModelAttribute("quizLists") Quiz quiz){
//        int couter = 0;
//        for (Question oneQuestion : quiz.getQuestionList()){
//            if(oneQuestion.getTrueAnswer() == quiz.getAnswerUser().get(oneQuestion.getID_Question() - 1)){
//                couter++;
//                finalQuiz.setScoreUser(couter);
//            }
//        }
//        log.info("Processing quiz: {}", quiz);
//        log.info("Answer user: {}", quiz.getAnswerUser());
//        log.info("Score user: {}", finalQuiz.getScoreUser());

        return "/resultForm";
    }
}

package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.model.Answer;
import com.qiuiz.quizFinal.model.Question;
import com.qiuiz.quizFinal.model.Quiz;
import com.qiuiz.quizFinal.repository.AnswerRepository;
import com.qiuiz.quizFinal.repository.QuestionRepository;
import com.qiuiz.quizFinal.repository.QuizRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/questions")
@SessionAttributes("Question")
public class QuestionsController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @ModelAttribute("Quest")
    public Question createNewQuest(){
        return new Question();
    }

    @RequestMapping(value = "/{id}/edit", params = {"saveQuestion"})
    public String saveQuestion(final Question editQuestion, Model model){
        log.info("Questions {}", editQuestion.getIdQuestion());
        log.info("ID Quiz {}" , editQuestion.getQuiz().getIdQuiz());
        for (Answer answer : editQuestion.getAnswers()){
            log.info("Answer: {}", answer.getAnswerId());
            log.info("Answer isCheked: {}", answer.isAnswer());
            if(answer.getAnswerId() == 0){
                answer.setQuestion(editQuestion);
                log.info("Answer to add: {}",answer.getAnswerId());
                answerRepository.save(answer);
            }
        }
        model.addAttribute("editQuestion", editQuestion);
        return "editQuestions";
    }

    @RequestMapping(value = "/{id}/edit", params = {"deleteAnswer"})
    public String deleteAnswer(final Question editQuestion,final HttpServletRequest req, Model model){
        final Integer coutAnswer = Integer.valueOf(req.getParameter("deleteAnswer"));
        log.info("Answer: {}", editQuestion.getAnswers());
        if(editQuestion.getAnswers().get(coutAnswer.intValue()).getAnswerId() != 0){
            answerRepository.deleteById(editQuestion.getAnswers().get(coutAnswer.intValue()).getAnswerId());
        }
        editQuestion.getAnswers().remove(coutAnswer.intValue());
        log.info("Answer after: {}", editQuestion.getAnswers());
        model.addAttribute("editQuestion",editQuestion);
        return "editQuestions";
    }

    @RequestMapping(value = "/{id}/edit", params = {"addNewAnswer"})
    public String addNewAnswer(final Question editQuestion, Model model){
        log.info("ID questions: ", editQuestion.getIdQuestion());
        log.info("Des questions: ", editQuestion.getDescription());
        editQuestion.getAnswers().add(new Answer());
        model.addAttribute("editQuestion",editQuestion);
        return "editQuestions";
    }

    @GetMapping("/{id}/edit")
    public String editQuestion(@PathVariable("id") int id, Model model){
        log.info("ID question: {}",id);
        model.addAttribute("editQuestion",questionRepository.findById(id).get());
        return "/editQuestions";
    }
}

package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.model.Answer;
import com.qiuiz.quizFinal.model.Question;
import com.qiuiz.quizFinal.model.Quiz;
import com.qiuiz.quizFinal.model.support.AnswerUser;
import com.qiuiz.quizFinal.repository.AnswerRepository;
import com.qiuiz.quizFinal.repository.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/game")
@SessionAttributes("quizes")
public class GamesController {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @RequestMapping(value = "/{id}/start", params = {"nextQuestion"})
    public String nextQuestions(final Question question, final AnswerUser answerUser, Model model){
        log.info("ID Questions: {}", question.getIdQuestion());
        log.info("Answer user: {}", answerUser);
        log.info("Answer to questions: {}", question.getAnswers());
        log.info("ID quiz: {}", question.getQuiz());
        log.info("List questions: {}", quizRepository.findById(question.getQuiz().getIdQuiz()).get().getQuestions());
        if(quizRepository.findById(question.getQuiz().getIdQuiz()).get().getQuestions().size() == answerUser.getCouter()){
            model.addAttribute("points", answerUser.getPoint());
            return "quizGame/scoreGame";
        }
        model.addAttribute("questionsList", quizRepository.findById(question.getQuiz().getIdQuiz()).get().getQuestions().get(answerUser.getCouter()));
        for (int id : answerUser.getCheckedItems()){
            if(answerRepository.findById(id).get().isAnswer() == true){
                int points = answerUser.getPoint();
                points++;answerUser.setPoint(points);
                log.info("Points: {}", points);
            }
        }
        int couter = answerUser.getCouter();
        couter++;
        answerUser.setCouter(couter);
        log.info("Update couter: {}", couter);
        model.addAttribute("answerUser", answerUser);
        return "quizGame/startGame";
    }

    @GetMapping("/scoreGame")
    public String scoresGame(int points, Model model){
        model.addAttribute("points", points);
        return "quizGame/scoreGame";
    }

    @GetMapping()
    public String quizGame(Model model){
        model.addAttribute("allQuiz", quizRepository.findAll());
        return "quizGame/quizGames";
    }

    @GetMapping("/{id}")
    public String showQuiz(@PathVariable int id, Model model){
        model.addAttribute("goQuiz",quizRepository.findById(id).get());
        return "quizGame/show";
    }

    @GetMapping("/{id}/start")
    public String startQuiz(@PathVariable int id, Model model, AnswerUser answerUser){
        model.addAttribute("answerUser", answerUser);
        model.addAttribute("questionsList", quizRepository.findById(id).get().getQuestions().get(0));
        return "quizGame/startGame";
    }
}

package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.model.Answer;
import com.qiuiz.quizFinal.model.Question;
import com.qiuiz.quizFinal.model.Quiz;
import com.qiuiz.quizFinal.model.support.AnswerUser;
import com.qiuiz.quizFinal.repository.AnswerRepository;
import com.qiuiz.quizFinal.repository.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
@RequestMapping("/game")
@SessionAttributes("quizes")
public class GamesController {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @RequestMapping(value = "/{id}/start", params = {"checkResult"})
    public String checkResult(final Quiz quiz,final AnswerUser answerUser,Model model){
        int points = 0;
        log.info("Quiz ID: {}", quiz.getIdQuiz());
        log.info("Answer user: {}", answerUser.getCheckedItems());
        for (int i = 0; i < answerUser.getCheckedItems().size(); i++) {
            Answer answer = answerRepository.findById(answerUser.getCheckedItems().get(i)).get();
            if(answer.isAnswer() == true){
                points++;
            }
        }
        log.info("Points: {}", points);
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
        model.addAttribute("quizGame", quizRepository.findById(id).get());
        return "quizGame/startGame";
    }
}

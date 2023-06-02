package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.model.Answer;
import com.qiuiz.quizFinal.model.HistoryGames;
import com.qiuiz.quizFinal.model.Question;
import com.qiuiz.quizFinal.model.Quiz;
import com.qiuiz.quizFinal.model.support.AnswerUser;
import com.qiuiz.quizFinal.repository.AnswerRepository;
import com.qiuiz.quizFinal.repository.HistoryRepository;
import com.qiuiz.quizFinal.repository.QuizRepository;
import com.qiuiz.quizFinal.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Controller
@RequestMapping("/game")
@SessionAttributes("quizes")
public class GamesController {
    private Quiz quizGame;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/{id}/start", params = {"nextQuestion"})
    public String nextQuestions(final Question question, final AnswerUser answerUser, Model model){
        log.info("Answer to questions: {}", question.getAnswers());
        log.info("ID quiz: {}", question.getQuiz());
        log.info("List questions: {}", quizRepository.findById(question.getQuiz().getIdQuiz()).get().getQuestions());
        for (int id : answerUser.getCheckedItems()){
            if(answerRepository.findById(id).get().isAnswer()){
                int points = answerUser.getPoint();
                points++;answerUser.setPoint(points);
                log.info("Points: {}", points);
            }
        }
        if(quizRepository.findById(question.getQuiz().getIdQuiz()).get().getQuestions().size() == answerUser.getCouter()){
            model.addAttribute("points", answerUser.getPoint());
            return "quizGame/scoreGame";
        }
        model.addAttribute("questionsList", quizRepository.findById(question.getQuiz().getIdQuiz()).get().getQuestions().get(answerUser.getCouter()));
        int couter = answerUser.getCouter();
        couter++;
        answerUser.setCouter(couter);
        log.info("Update couter: {}", couter);
        model.addAttribute("answerUser", answerUser);
        return "quizGame/startGame";
    }

    @PostMapping("/scoreGame")
    public String saveScoreUser(Model model, final HttpServletRequest req){
        final Integer pointUser = Integer.valueOf(req.getParameter("saveScoreUser"));

        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loginUser", login);
        model.addAttribute("idUser",userRepository.findByUsername(login));
        log.info("Eject: {}",login);
        LocalDate now = LocalDate.now();
        Date date = Date.valueOf(now);
        log.info("Quiz ID: {}",quizGame.getIdQuiz());
        log.info("User ID: {}",userRepository.findByUsername(login).getIduser());
        historyRepository.save(new HistoryGames(quizGame,userRepository.findByUsername(login),
                pointUser,date));

        return "home";
    }

    @GetMapping("/scoreGame")
    public String scoresGame(int points, Model model, AnswerUser answerUser){
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
        quizGame = quizRepository.findById(id).get();
        model.addAttribute("goQuiz",quizGame);
        return "quizGame/show";
    }

    @GetMapping("/{id}/start")
    public String startQuiz(@PathVariable int id, Model model, AnswerUser answerUser){
        quizGame = quizRepository.findById(id).get();
        model.addAttribute("answerUser", answerUser);
        model.addAttribute("questionsList", quizRepository.findById(id).get().getQuestions().get(0));
        return "quizGame/startGame";
    }
}

package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.model.Question;
import com.qiuiz.quizFinal.model.Quiz;
import com.qiuiz.quizFinal.repository.QuestionRepository;
import com.qiuiz.quizFinal.repository.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/quiz")
@SessionAttributes("Quiz")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @ModelAttribute("Quiz")
    public Quiz createQuiz(){
        return new Quiz();
    }

    @RequestMapping(value = "/new", params = {"addNewQuiz"})
    public String addNewQuiz(final Quiz quiz){
        log.info("Name new quiz: {}", quiz.getNameQuiz());
        quizRepository.save(quiz);
        return "redirect:/quiz/all";
    }

    @RequestMapping(value = "/{id}/edit", params = {"addNewQuestion"})
    public String addNewQuestion(final Quiz editQuiz, final BindingResult bindingResult, Model model){
        log.info("Name Quiz: {}", editQuiz.getNameQuiz());
        log.info("ID Quiz: {}", editQuiz.getIdQuiz());
        log.info("List question: {}", editQuiz.getQuestions());
        editQuiz.getQuestions().add(new Question("",editQuiz));
        log.info("List question: {}", editQuiz.getQuestions());
        model.addAttribute("editQuiz",editQuiz);
        return "edit";
    }

    @GetMapping("/new")
    public String createNewQuiz(){
        return "new";
    }

    @GetMapping("/all")
    public String showAllQuiz(Model model){
        model.addAttribute("listQuiz", quizRepository.findAll());
        return "/quizAll";
    }

    @GetMapping("/{id}")
    public String showQuiz(@PathVariable("id") int id, Model model){
        model.addAttribute("showQuiz",quizRepository.findById(id).get());
        return "show";
    }

    @GetMapping("/{id}/edit")
    public String editQuiz(@PathVariable("id") int id, Model model){
        model.addAttribute("editQuiz",quizRepository.findById(id).get());
        return "/edit";
    }
}

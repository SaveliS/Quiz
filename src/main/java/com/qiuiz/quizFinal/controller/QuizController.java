package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.model.Answer;
import com.qiuiz.quizFinal.model.Question;
import com.qiuiz.quizFinal.model.Quiz;
import com.qiuiz.quizFinal.repository.AnswerRepository;
import com.qiuiz.quizFinal.repository.QuestionRepository;
import com.qiuiz.quizFinal.repository.QuizRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/quiz")
@SessionAttributes("Quiz")
public class QuizController {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @ModelAttribute("Quiz")
    public Quiz createQuiz(){
        return new Quiz();
    }

    @RequestMapping(value = "/new", params = {"addNewQuiz"})
    public String addNewQuiz(final Quiz quiz,final Question question, Model model){
        log.info("Name new quiz: {}", quiz.getNameQuiz());
        log.info("ID new quiz: {}",quiz.getIdQuiz());
        log.info("List question: {}", quiz.getQuestions());
        quiz.getQuestions().add(new Question());
        log.info("List after add: {}", quiz.getQuestions());
        model.addAttribute("Quiz",quiz);
        return "new";
    }

    @RequestMapping(value = "/new",params = {"addNewAnswer"})
    public String addNewAnswer(final Quiz quiz, Model model, final HttpServletRequest req){
        final Integer QuestionID = Integer.valueOf(req.getParameter("addNewAnswer"));
        log.info("List answers: {}", quiz.getQuestions().get(QuestionID).getAnswers());
        quiz.getQuestions().get(QuestionID).getAnswers().add(new Answer());
        log.info("ID Questions: {}",QuestionID.intValue());
        log.info("List answers: {}", quiz.getQuestions().get(QuestionID).getAnswers());
        model.addAttribute("Quiz", quiz);
        return "new";
    }

    @RequestMapping(value = "/new",params = {"saveNewQuiz"})
    public String saveNewAnswer(final Quiz quiz){
        quizRepository.save(quiz);
        for (Question question : quiz.getQuestions()){
            log.info("Question list {} {}:", question.getIdQuestion(), question.getDescription());
            question.setQuiz(quiz);
            questionRepository.save(question);
            for(Answer answer: question.getAnswers()){
                log.info("Answer list {} {}:",answer.getAnswerId(), answer.getDescription());
                answer.setQuestion(question);
                answerRepository.save(answer);
            }
        }
        return "redirect:/quiz/all";
    }

    @RequestMapping(value = "/{id}/edit", params = {"addNewAnswer"})
    public String addNewAnswerEditForm(final Quiz editQuiz, final HttpServletRequest req, Model model){
        final Integer QuestionID = Integer.valueOf(req.getParameter("addNewAnswer"));
        log.info("Couter answer: {}", QuestionID);
        editQuiz.getQuestions().get(QuestionID).getAnswers().add(new Answer());
        model.addAttribute("editQuiz",editQuiz);
        return "edit";
    }

    @RequestMapping(value = "/{id}/edit", params = {"addNewQuestions"})
    public String addNewQuestionsEditForm (final Quiz editQuiz, Model model){
        log.info("ID Quiz: {}", editQuiz.getIdQuiz());
        editQuiz.getQuestions().add(new Question());
        model.addAttribute("editQuiz", editQuiz);
        return "edit";
    }

    @RequestMapping(value = "/{id}/edit", params = {"saveEditQuestions"})
    public String saveEditQuiz(final Quiz editQuiz){
        for (Question question : editQuiz.getQuestions()){
            question.setQuiz(editQuiz);
            if(question.getIdQuestion() == 0){
                log.info("Question ID: {}. Question des: {}. Questions to quiz: {}",question.getIdQuestion(), question.getDescription(), question.getQuiz().getIdQuiz());
                questionRepository.save(question);
            }
            for (Answer answer: question.getAnswers()){
                answer.setQuestion(question);
                log.info("Answer description: {}. Answer id: {}. Answer to question: {}.", answer.getDescription(),answer.getAnswerId(),answer.getQuestion().getIdQuestion());
                answerRepository.save(answer);
            }
        }
        return "redirect:/quiz/all";
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

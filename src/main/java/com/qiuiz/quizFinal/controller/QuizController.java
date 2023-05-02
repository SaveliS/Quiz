package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.model.Answer;
import com.qiuiz.quizFinal.model.Question;
import com.qiuiz.quizFinal.model.Quiz;
import com.qiuiz.quizFinal.repository.AnswerRepository;
import com.qiuiz.quizFinal.repository.QuestionRepository;
import com.qiuiz.quizFinal.repository.QuizRepository;
import com.qiuiz.quizFinal.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/quiz")
@SessionAttributes("Quiz")
public class QuizController {

    private List<Question> allQuestionInQuiz = new ArrayList<>();
    private Quiz newQuizs;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    /**
     * Репозиторий для управления пользователями из БД.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * @return Новый объект Quiz, не добавленный в БД.
     * Создает новый пустой объект Quiz, для создания новой викторины.
     */
    @ModelAttribute("Quiz")
    public Quiz createQuiz() {
        return new Quiz();
    }

    @RequestMapping(value = "/new",params = {"loadImageQuestion"})
    public String loadImageQuestion(final Quiz quiz,final HttpServletRequest reg, Model model){
        final Integer questionID = Integer.valueOf(reg.getParameter("loadImageQuestion"));
        newQuizs = quiz;
        model.addAttribute("question", quiz.getQuestions().get(questionID));
        return "newQuiz/imageQuestion";
    }

    @RequestMapping(value = "/new", params = {"addNewAnswer"})
    public String addNewAnswer(final Quiz quiz, Model model){
        quiz.getQuestions().get(quiz.getQuestions().size() - 1).getAnswers().add(new Answer());
        log.info("Quiz: {}", quiz.getNameQuiz());
        model.addAttribute("newQuiz", quiz);
        return "newQuiz/newQuestions";
    }

    @RequestMapping(value = "/new",params = {"checkNewQuiz"})
    public String checkNewQuiz(final Quiz quiz,BindingResult result, Model model){
//        if(result.hasErrors()){
//            model.addAttribute("errors", result.getAllErrors());
//            return "newQuiz/newQuestions";
//        }
        allQuestionInQuiz.add(quiz.getQuestions().get(quiz.getQuestions().size() - 1));
        quiz.setQuestions(allQuestionInQuiz);
        model.addAttribute("finalQuiz", quiz);
        return "newQuiz/newQuizFinal";
    }

    @RequestMapping(value = "/new",params = {"addNewQuestion"})
    public String addNewQuestion(@Valid final Quiz quiz, @Valid Question question,BindingResult result, Model model) {
//        if(result.hasErrors()){
//            model.addAttribute("errors", result.getAllErrors());
//            model.addAttribute("newQuiz", quiz);
//            return "newQuiz/newQuestions";
//        }
        allQuestionInQuiz.add(quiz.getQuestions().get(quiz.getQuestions().size() - 1));
        quiz.getQuestions().add(CreateQuest(quiz));
        model.addAttribute("newQuiz", quiz);
        return "newQuiz/newQuestions";
    }

    @RequestMapping(value = "/new", params = {"SaveNewQuiz"})
    public String SaveNewQuiz(final Quiz  quiz,Model model){
        quiz.setQuestions(allQuestionInQuiz);
        quizRepository.save(quiz);
        for (Question question: allQuestionInQuiz){
            question.setQuiz(quiz);
            questionRepository.save(question);
            for (Answer answer : question.getAnswers()){
                answer.setQuestion(question);
                log.info("Answer question ID: {}", answer.getQuestion().getIdQuestion());
                log.info("Answer description: {}", answer.getDescription());
                log.info("Answer ID: {}", answer.getAnswerId());
                answerRepository.save(answer);
                log.info("Answer question ID: {}", answer.getQuestion().getIdQuestion());
                log.info("Answer description: {}", answer.getDescription());
                log.info("Answer ID: {}", answer.getAnswerId());
            }
        }

        return cancelCreateQuiz(model);
    }

    /**
     * @param quiz Викторина с названием, не добавлена в БД.
     * @param result Валидатор (ошибки), которые могли быть допущены.
     * @param model Модель для данных.
     * @return
     * Передает объект Quiz для дальнейшего взаимодействия.
     */
    @RequestMapping(value = "/new", params = {"saveNewQuiz"})
    public  String addNewQuiz(@Valid final Quiz quiz, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("errors", result.getAllErrors());
            return "newQuiz/newQuizs";
        }
        quiz.getQuestions().add(CreateQuest(quiz));
        model.addAttribute("newQuiz", quiz);
        return "newQuiz/newQuestions";
    }

    private Question CreateQuest(Quiz quiz){
        Question question = new Question();
        List<Answer> answerList = new ArrayList<>();
        answerList.add(new Answer());
        answerList.add(new Answer());
        question.setAnswers(answerList);
        question.setQuiz(quiz);
        log.info("CreateQuest: {}",quiz.getQuestions());
        return question;
    }

    /**
     * @param model Модель для данных.
     * @return Страница home.
     */
    @RequestMapping(value = "/new", params = {"cancelCreateQuiz"})
    public String cancelCreateQuiz(Model model){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loginUser", login);
        model.addAttribute("idUser",userRepository.findByUsername(login));
        return "home";
    }

    @RequestMapping(value = "/{id}/edit", params = {"addNewAnswer"})
    public String addNewAnswerEditForm(final Quiz editQuiz, final HttpServletRequest req, Model model) {
        final Integer QuestionID = Integer.valueOf(req.getParameter("addNewAnswer"));
        log.info("Couter answer: {}", QuestionID);
        editQuiz.getQuestions().get(QuestionID).getAnswers().add(new Answer());
        model.addAttribute("editQuiz", editQuiz);
        return "edit";
    }

    @RequestMapping(value = "/{id}/edit", params = {"addNewQuestions"})
    public String addNewQuestionsEditForm(final Quiz editQuiz, Model model) {
        log.info("ID Quiz: {}", editQuiz.getIdQuiz());
        editQuiz.getQuestions().add(new Question());
        model.addAttribute("editQuiz", editQuiz);
        return "edit";
    }

    @RequestMapping(value = "/{id}/edit", params = {"saveEditQuestions"})
    public String saveEditQuiz(final Quiz editQuiz) {
        for (Question question : editQuiz.getQuestions()) {
            question.setQuiz(editQuiz);
            if (question.getIdQuestion() == 0) {
                log.info("Question ID: {}. Question des: {}. Questions to quiz: {}", question.getIdQuestion(), question.getDescription(), question.getQuiz().getIdQuiz());
                questionRepository.save(question);
            }
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(question);
                log.info("Answer description: {}. Answer id: {}. Answer to question: {}.", answer.getDescription(), answer.getAnswerId(), answer.getQuestion().getIdQuestion());
                answerRepository.save(answer);
            }
        }
        return "redirect:/quiz/all";
    }

    @GetMapping("/new")
    public String createNewQuiz() {
        return "newQuiz/newQuizs";
    }

    @GetMapping("/all")
    public String showAllQuiz(Model model) {
        model.addAttribute("listQuiz", quizRepository.findAll());
        return "/quizAll";
    }

    @GetMapping("/{id}")
    public String showQuiz(@PathVariable("id") int id, Model model) {
        model.addAttribute("showQuiz", quizRepository.findById(id).get());
        return "show";
    }

    @GetMapping("/{id}/edit")
    public String editQuiz(@PathVariable("id") int id, Model model) {
        model.addAttribute("editQuiz", quizRepository.findById(id).get());
        return "/edit";
    }

    @PostMapping("/new/updateImage")
    public String updateImage(@RequestParam MultipartFile file, Model model) throws IOException {
        newQuizs.getQuestions().get(newQuizs.getQuestions().size() - 1).setImage_questions(file.getBytes());
        model.addAttribute("newQuiz", newQuizs);
        return "newQuiz/newQuestions";
    }
}

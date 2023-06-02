package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.model.*;
import com.qiuiz.quizFinal.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/quiz")
public class QuizController {

    private final List<Question> allQuestionInQuiz = new ArrayList<>();
    private Quiz newQuizs;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private HistoryRepository historyRepository;

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
        if(quiz.getQuestions().get(quiz.getQuestions().size() - 1).getImage_questions().length == 0){
            quiz.getQuestions().get(quiz.getQuestions().size() - 1).setImage_questions(null);
        }
        quiz.getQuestions().get(quiz.getQuestions().size() - 1).getAnswers().add(new Answer());
        log.info("Quiz: {}", quiz.getNameQuiz());
        model.addAttribute("newQuiz", quiz);
        return "newQuiz/newQuestions";
    }

    @RequestMapping(value = "/new",params = {"checkNewQuiz"})
    public String checkNewQuiz(final Quiz quiz,BindingResult result, Model model){
        allQuestionInQuiz.add(quiz.getQuestions().get(quiz.getQuestions().size() - 1));
        quiz.setQuestions(allQuestionInQuiz);
        model.addAttribute("finalQuiz", quiz);
        return "newQuiz/newQuizFinal";
    }

    @RequestMapping(value = "/new",params = {"addNewQuestion"})
    public String addNewQuestion(@Valid final Quiz quiz, @Valid Question question,BindingResult result, Model model) {
        allQuestionInQuiz.add(quiz.getQuestions().get(quiz.getQuestions().size() - 1));
        quiz.getQuestions().add(CreateQuest(quiz));
        model.addAttribute("newQuiz", quiz);
        return "newQuiz/newQuestions";
    }

    @RequestMapping(value = "/new", params = {"SaveNewQuiz"})
    public String SaveNewQuiz(final Quiz  quiz,Model model, @AuthenticationPrincipal User autUser){
        User user = userRepository.findByUsername(autUser.getUsername());
        quiz.setQuestions(allQuestionInQuiz);
        if(user != null){
            quiz.setUser(user);
        }
        quizRepository.save(quiz);
        for (Question question: allQuestionInQuiz){
            question.setQuiz(quiz);
            if(question.getImage_questions().length == 0){
                question.setImage_questions(null);
            }
            questionRepository.save(question);
            for (Answer answer : question.getAnswers()){
                answer.setQuestion(question);
                answerRepository.save(answer);
            }
        }
        allQuestionInQuiz.clear();
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

    @RequestMapping(value = "/{id}/edit", params = {"removeImagesEditQuestion"})
    public String removeImageEditQuestion(final Quiz quiz, final HttpServletRequest reg, Model model){
        final Integer questionIndex = Integer.valueOf(reg.getParameter("removeImagesEditQuestion"));
        if(quiz.getQuestions().get(questionIndex).getIdQuestion() != 0){
            quiz.getQuestions().get(questionIndex).setImage_questions(null);
            questionRepository.save(quiz.getQuestions().get(questionIndex));
        }
        quiz.getQuestions().get(questionIndex).setImage_questions(null);
        model.addAttribute("editQuiz", quiz);
        return "editQuiz/editQuiz";
    }

    private Question updateImageQuest;
    @RequestMapping(value = "/{id}/edit", params = {"updateImageQuestion"})
    public String updateImageEditQuestion(final Quiz quiz, final HttpServletRequest reg ,Model model){
        final Integer questionIndex = Integer.valueOf(reg.getParameter("updateImageQuestion"));
        quiz.getQuestions().get(questionIndex).setQuiz(quiz);
        updateImageQuest = quiz.getQuestions().get(questionIndex);
        model.addAttribute("quiz", quiz);
        model.addAttribute("questionIndex", questionIndex);
        return "editQuiz/editImage";
    }

    @GetMapping("/{id}/remove")
    public String RemoveQuiz(@PathVariable("id") int id,
                             @RequestParam(value = "confirmed", required = false) Boolean confirmed,
                             Model model, @AuthenticationPrincipal User user){
        if(confirmed){
            for (Question question: quizRepository.findById(id).get().getQuestions()){
                for (Answer answer: question.getAnswers()){
                    answerRepository.deleteById(answer.getAnswerId());
                }
                questionRepository.deleteById(question.getIdQuestion());
            }
            for (HistoryGames history: quizRepository.findById(id).get().getHistoryGames()){
                historyRepository.deleteById(history.getId());
            }
            quizRepository.deleteById(id);
        }
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loginUser", login);
        model.addAttribute("idUser",userRepository.findByUsername(login));
        log.info("Name login: {}", login);
        return "home";
    }

    @RequestMapping(value = "/{id}/edit", params = {"cancelEditQuiz"})
    public String cancelEditQuiz(Model model){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loginUser", login);
        model.addAttribute("idUser",userRepository.findByUsername(login));
        return "home";
    }

    @RequestMapping(value = "/{id}/edit", params = {"addNewAnswer"})
    public String addNewAnswerEditForm(final Quiz editQuiz, final HttpServletRequest req, Model model) {
        final Integer QuestionIndex = Integer.valueOf(req.getParameter("addNewAnswer"));
        log.info("Couter answer: {}", QuestionIndex);
        editQuiz.getQuestions().get(QuestionIndex).getAnswers().add(new Answer());
        model.addAttribute("editQuiz", editQuiz);
        return "editQuiz/editQuiz";
    }

    @RequestMapping(value = "/{id}/edit", params = {"addNewQuestions"})
    public String addNewQuestionsEditForm(final Quiz editQuiz, Model model) {
        log.info("ID Quiz: {}", editQuiz.getIdQuiz());
        Question newQuestion = new Question();
        newQuestion.getAnswers().add(new Answer());
        newQuestion.getAnswers().add(new Answer());
        editQuiz.getQuestions().add(newQuestion);
        model.addAttribute("editQuiz", editQuiz);
        return "editQuiz/editQuiz";
    }

    @RequestMapping(value = "/{id}/edit", params = {"removeEditQuestion"})
    public String RemoveQuestion(final Quiz editQuiz, final HttpServletRequest reg, Model model){
        final int indexQuestion = Integer.valueOf(reg.getParameter("removeEditQuestion"));
        if(editQuiz.getQuestions().get(indexQuestion).getIdQuestion() != 0){
            for(Answer answer: editQuiz.getQuestions().get(indexQuestion).getAnswers()){
                answerRepository.deleteById(answer.getAnswerId());
            }
            questionRepository.deleteById(editQuiz.getQuestions().get(indexQuestion).getIdQuestion());
        }
        log.info("Quiz question: {}", editQuiz.getQuestions());
        editQuiz.getQuestions().remove(indexQuestion);
        log.info("Quiz question: {}", editQuiz.getQuestions());
        model.addAttribute("editQuiz", editQuiz);
        return "editQuiz/editQuiz";
    }

    @RequestMapping(value = "/{id}/edit", params = {"removeEditAnswer"})
    public String RemoveAnswer(final Quiz editQuiz,final HttpServletRequest reg, Model model){
        // Получаем параметры: Индекс вопроса, Индекс ответа.
        String valueForms = reg.getParameter("removeEditAnswer");
        String [] value = valueForms.split(":");
        log.info("\nEdit question: {}", value[0]);
        log.info("\nEdit answer: {}", value[1]);

        // Находим данный вопрос в Quiz, удаляем от туда ответ по индексу.
        int indexAnswer = Integer.valueOf(value[1]);
        // Если записи нет в БД, то просто удаляем из списка.
        if(editQuiz.getQuestions().get(Integer.valueOf(value[0])).getAnswers().get(indexAnswer).getAnswerId() != 0){
            answerRepository.deleteById(editQuiz.getQuestions().get(Integer.valueOf(value[0])).getAnswers().get(indexAnswer).getAnswerId());
        }
        editQuiz.getQuestions().get(Integer.valueOf(value[0])).getAnswers().remove(indexAnswer);
        // Отправляем обратно измененную викторину.
        model.addAttribute("editQuiz", editQuiz);
        return "editQuiz/editQuiz";
    }

    @RequestMapping(value = "/{id}/edit", params = {"saveEditQuestions"})
    public String saveEditQuiz(Quiz editQuiz, Model model, @AuthenticationPrincipal User user) {
        for (Question question : editQuiz.getQuestions()) {
            question.setQuiz(editQuiz);
            log.info("Question ID: {}. \nQuestion des: {}. \nQuestions to quiz: {}.\n Question Image: {}.", question.getIdQuestion(), question.getDescription(), question.getQuiz().getIdQuiz(), question.getImage_questions());
            if (question.getIdQuestion() == 0) {
                questionRepository.save(question);
            }
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(question);
                log.info("Answer description: {}. Answer id: {}. Answer to question: {}.", answer.getDescription(), answer.getAnswerId(), answer.getQuestion().getIdQuestion());
                answerRepository.save(answer);
            }
        }
        model.addAttribute("currentUser", userRepository.findById(user.getIduser()).get());
        return "profile/userQuizList";
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
        return "editQuiz/editQuiz";
    }


    @PostMapping("/new/updateImage")
    public String updateImage(@RequestParam MultipartFile file, Model model) throws IOException {
        newQuizs.getQuestions().get(newQuizs.getQuestions().size() - 1).setImage_questions(file.getBytes());
        model.addAttribute("newQuiz", newQuizs);
        return "newQuiz/newQuestions";
    }

    @PostMapping("/{id}/edit/image")
    public String updateImageEditQuestion(@RequestParam MultipartFile file, Model model,
                                          final Quiz quiz, @RequestParam String questionIndex) throws IOException{
        log.info("Quiz ID: {} \n Quiz question: {}", quiz.getIdQuiz(), quiz.getQuestions());
        log.info("Question Index: {}", questionIndex);
        quiz.getQuestions().set(Integer.valueOf(questionIndex),updateImageQuest);
        quiz.getQuestions().get(Integer.valueOf(questionIndex)).setImage_questions(file.getBytes());
        questionRepository.save(quiz.getQuestions().get(Integer.valueOf(questionIndex)));
        model.addAttribute("editQuiz", quiz);
        return "editQuiz/editQuiz";
    }

}

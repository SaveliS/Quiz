package com.qiuiz.quizFinal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @Column(name = "idquiz")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuiz;
    @Column(name = "namequiz")
    @NotBlank(message = "Заполните поле.")
    @Size(min = 3, message = "Название должно содержать минимум 3 символа.")
    private String nameQuiz;

    @OneToMany(mappedBy = "quiz")
    @Fetch(FetchMode.JOIN)
    private List<Question> questions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="id_user")
    private User user;
    @OneToMany(mappedBy = "quiz_history")
    private List<HistoryGames> historyGames = new ArrayList<>();

    public Quiz( String nameQuiz) {
        this.nameQuiz = nameQuiz;
    }

    public Quiz() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public String getNameQuiz() {
        return nameQuiz;
    }

    public void setNameQuiz(String nameQuiz) {
        this.nameQuiz = nameQuiz;
    }

    public List<HistoryGames> getHistoryGames() {
        return historyGames;
    }

    public void setHistoryGames(List<HistoryGames> historyGames) {
        this.historyGames = historyGames;
    }
}

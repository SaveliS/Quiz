package com.qiuiz.quizFinal.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @Column(name = "idquiz")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuiz;
    @Column(name = "namequiz")
    private String nameQuiz;

    @OneToMany(mappedBy = "quiz")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "quiz_history")
    private List<HistoryGames> historyGames = new ArrayList<>();

    public Quiz( String nameQuiz) {
        this.nameQuiz = nameQuiz;
    }

    public Quiz() {
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

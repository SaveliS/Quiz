package com.qiuiz.quizFinal.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "history_game")
public class HistoryGames {
    @Id
    @Column(name = "id_history")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="id_quiz")
    private Quiz quiz_history;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User current_user;

    @Column(name = "number_points")
    private int points;

    @Column(name = "date_quiz")
    private Date date_quiz;

    public HistoryGames() {
    }

    public HistoryGames(Quiz quiz_history, User current_user, int points, Date date_quiz) {
        this.quiz_history = quiz_history;
        this.current_user = current_user;
        this.points = points;
        this.date_quiz = date_quiz;
    }

    public int getId() {
        return id;
    }

    public Quiz getQuiz_history() {
        return quiz_history;
    }

    public void setQuiz_history(Quiz quiz_history) {
        this.quiz_history = quiz_history;
    }

    public User getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(User current_user) {
        this.current_user = current_user;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getDate_quiz() {
        return date_quiz;
    }

    public void setDate_quiz(Date date_quiz) {
        this.date_quiz = date_quiz;
    }
}

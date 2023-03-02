package com.qiuiz.quizFinal.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @Column(name = "idquestion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuestion;
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "idquiz",nullable = false)
    private Quiz quiz;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    public Question() {
    }

    public Question(String description, Quiz quiz) {
        this.description = description;
        this.quiz = quiz;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}

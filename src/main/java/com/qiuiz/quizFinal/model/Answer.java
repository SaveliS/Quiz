package com.qiuiz.quizFinal.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @Column(name = "idanswer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int answerId;
    @Column(name = "description")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "idquestion", nullable = false)
    private Question question;

    public Answer() {
    }

    public Answer(int answerId, String description, Question question) {
        this.answerId = answerId;
        this.description = description;
        this.question = question;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}

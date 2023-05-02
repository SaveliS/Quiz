package com.qiuiz.quizFinal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @Column(name = "idanswer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int answerId;
    @Column(name = "description")
    @NotBlank(message = "Заполните поле.")
    @Size(min = 3, message = "Название должно содержать минимум 3 символа.")
    private String description;
    @Column(name = "isanswer")
    private boolean isAnswer;

    @ManyToOne()
    @JoinColumn(name = "idquestion", nullable = false)
    private Question question;

    public Answer() {
    }

    public Answer(int answerId, String description, Question question, boolean isAnswer) {
        this.isAnswer = isAnswer;
        this.answerId = answerId;
        this.description = description;
        this.question = question;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
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

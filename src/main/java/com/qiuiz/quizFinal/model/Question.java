package com.qiuiz.quizFinal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @Column(name = "idquestion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuestion;
    @Size(min = 3, message = "Вопрос должно содержать минимум 3 символа")
    @NotBlank(message = "Заполните вопрос.")
    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private byte [] image_questions;

    @ManyToOne
    @JoinColumn(name = "idquiz",nullable = false)
    private Quiz quiz;


    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    public Question() {
    }

    public Question(String description, Quiz quiz, byte [] image) {
        this.description = description;
        this.quiz = quiz;
        this.image_questions = image;
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

    public byte[] getImage_questions() {
        if(image_questions == null){
            return null;
        }
        return image_questions;
    }

    public String getPhotoBase64() {
        if(image_questions == null)
        {
            return  null;
        }

        return Base64.getEncoder().encodeToString(getImage_questions());
    }



    public void setImage_questions(byte[] image_questions) {
        this.image_questions = image_questions;
    }
}

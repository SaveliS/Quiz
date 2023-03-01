package com.qiuiz.quizFinal.model;

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
    private Set<Question> questions = new HashSet<>();

    public Quiz( String nameQuiz) {
        this.nameQuiz = nameQuiz;
    }

    public Quiz() {
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestionsAsList(){
        return new ArrayList<Question>(questions);
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
}

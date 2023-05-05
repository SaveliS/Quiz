package com.qiuiz.quizFinal.model.support;

import com.qiuiz.quizFinal.model.Answer;
import com.qiuiz.quizFinal.model.Question;
import com.qiuiz.quizFinal.model.Quiz;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AnswerUser {
    private List<Integer> checkedItems = new ArrayList<>();
    private int couter = 1;
    private int point = 0;

    public AnswerUser(List<Integer> checkedItems, int couter, int point) {
        this.point = point;
        this.couter = couter;
        this.checkedItems = checkedItems;
    }

    public AnswerUser() {
    }

    public List<Integer> getCheckedItems() {
        return checkedItems;
    }

    public void setCheckedItems(List<Integer> checkedItems) {
        this.checkedItems = checkedItems;
    }

    public int getCouter() {
        return couter;
    }

    public void setCouter(int couter) {
        this.couter = couter;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

}

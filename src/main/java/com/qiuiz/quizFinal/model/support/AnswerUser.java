package com.qiuiz.quizFinal.model.support;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AnswerUser {
    private List<Integer> checkedItems = new ArrayList<>();

    public AnswerUser(List<Integer> checkedItems) {
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
}

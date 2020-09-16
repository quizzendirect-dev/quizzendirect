package fr.univ.angers.quizz.api.model;

import java.io.Serializable;
import java.util.List;

public class QuestionList implements Serializable {
    private List<Question> questions;
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }


}

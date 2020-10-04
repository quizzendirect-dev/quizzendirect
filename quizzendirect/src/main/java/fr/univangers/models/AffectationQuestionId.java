package fr.univangers.models;

import java.io.Serializable;

public class AffectationQuestionId implements Serializable {
    private int id_quest;
    private int id_rep;

    public AffectationQuestionId(){}
    public AffectationQuestionId(int id_quest, int id_rep){
        this.id_quest = id_quest;
        this.id_rep = id_rep;
    }
}

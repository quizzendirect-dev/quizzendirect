package fr.univangers.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity(name = "affectationquestion")
@IdClass(AffectationQuestionId.class)
public class AffectationQuestion implements Serializable {

    @Id
    private int id_quest;
    @Id
    private int id_rep;

    public AffectationQuestion(){}
    public AffectationQuestion(int id_quest, int id_rep){
        this.id_quest = id_quest;
        this.id_rep = id_rep;
    }

    public void setId_quest(int id_quest) {this.id_quest = id_quest;}

    public int getId_quest() {
        return id_quest;
    }

    public void setId_rep(int id_rep) {
        this.id_rep = id_rep;
    }

    public int getId_rep() {
        return id_rep;
    }
}

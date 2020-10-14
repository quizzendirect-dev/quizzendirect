package fr.univ.angers.quizz.api.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "HISTORIQUE")
public class Historique {
    @Id
    private int id_quest;
    private String date; //Format : jj/mm/aaaa + heure

    public Historique() {}
    public Historique(String date) {
        this.date = date;
    }

    public void setId_quest(int id_quest) {this.id_quest = id_quest;}
    public int getId_quest() {return id_quest;}

    public void setDate(String date) {this.date = date;}
    public String getDate() {return date;}
}

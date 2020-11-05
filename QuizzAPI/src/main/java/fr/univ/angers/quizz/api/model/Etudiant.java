package fr.univ.angers.quizz.api.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ETUDIANT")
public class Etudiant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_etud;
    private String pseudo;
    @OneToOne
    @JoinColumn(name = "salon")
    private Salon salon;
    private int bonnesReponses = 0; //Pour les statistiques
    private int questionsRepondues = 0;

    public Etudiant(){}
    public Etudiant(String pseudo, Salon salon){
        this.pseudo = pseudo;
        this.salon = salon;
        this.bonnesReponses = 0;
        this.questionsRepondues = 0;
    }

    public int getId_etud() {return this.id_etud;}

    public void setPseudo(String pseudo) {this.pseudo = pseudo;}
    public String getPseudo() {return pseudo;}

    public void setSalon(Salon salon) {
        this.salon = salon;
    }
    public Salon getSalon() {
        return this.salon;
    }

    public void addBonneReponse() {this.bonnesReponses++;}
    public int getBonnesReponses() {return this.bonnesReponses;}

    public void addQuestionsRepondues() {this.questionsRepondues++;}
    public int getQuestionsRepondues() {return this.questionsRepondues;}
}

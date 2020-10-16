package fr.univ.angers.quizz.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "REPERTOIRE")
public class Repertoire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_rep;
    private String nom;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_rep")
    private List<Question> questions = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rep")
    private Professeur professeur;

    public Repertoire() {}
    public Repertoire(String nom, Professeur professeur){
        this.professeur = professeur;
        this.nom = nom;
    }

    public void setId_rep(int id_rep) {this.id_rep = id_rep;}
    public int getId_rep() {return id_rep;}

    public void setNom(String nom) {this.nom = nom;}
    public String getNom() {return nom;}

    public void setQuestions(List<Question> affectationsQuestions) {this.questions = questions;}
    public List<Question> getQuestions() {return questions;}
    public void addQuestion(Question question) {questions.add(question);}
    public void removeQuestion(Question question) {questions.remove(question);}

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }
    public Professeur getProfesseur() {
        return professeur;
    }
}

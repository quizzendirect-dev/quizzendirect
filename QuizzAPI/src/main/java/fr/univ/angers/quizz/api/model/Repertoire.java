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
    @OneToMany
    @JoinColumn(name = "id_rep")
    private List<Question> questions = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "id_rep")
    private Enseignant enseignant;

    public Repertoire() {}
    public Repertoire(String nom, Enseignant enseignant){
        this.enseignant = enseignant;
        this.nom = nom;
    }

    public int getId_rep() {return id_rep;}

    public void setNom(String nom) {this.nom = nom;}
    public String getNom() {return nom;}

    public void setQuestions(List<Question> questions) {this.questions = questions;}
    public List<Question> getQuestions() {return questions;}
    public void addQuestion(Question question) {questions.add(question);}
    public void removeQuestion(Question question) {questions.remove(question);}

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }
    public Enseignant getEnseignant() {
        return enseignant;
    }
}

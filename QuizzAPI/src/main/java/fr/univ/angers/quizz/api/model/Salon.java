package fr.univ.angers.quizz.api.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "SALON")
public class Salon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_salon;
    private int codeAcces;
    @OneToMany
    private List<Question> questionsEnAttente;
    @OneToMany
    private List<Question> questionsPosees;
    @OneToOne
    @JoinColumn(name = "questioncourante")
    private Question questionCourante;
    @OneToMany
    private List<Etudiant> etudiants;
    @OneToOne
    @JoinColumn(name = "enseignant")
    private Enseignant enseignant;

    public Salon(){}
    public Salon(int codeAcces, Enseignant enseignant){
        this.codeAcces = codeAcces;
        this.enseignant = enseignant;
    }

    public int getId_salon() {return id_salon;}

    public void setCodeAcces(int codeAcces) {this.codeAcces = codeAcces;}
    public int getCodeAcces() {return codeAcces;}

    public void setQuestionsEnAttente(List<Question> questionsEnAttente) {this.questionsEnAttente = questionsEnAttente; }
    public List<Question> getQuestionEnAttente() {return questionsEnAttente;}
    public void addQuestion(Question question) {questionsEnAttente.add(question);}
    public void removeQuestion(Question question) {questionsEnAttente.remove(question);}

    public List<Question> getQuestionPosees() {return this.questionsPosees;}

    public void setQuestionCourante(Question questionCourante) {this.questionCourante = questionCourante;}
    public Question getQuestionCourante() {return this.questionCourante;}

    public void setEtudiants(List<Etudiant> etudiants) {this.etudiants = etudiants;}
    public List<Etudiant> getEtudiants() {return this.etudiants;}
    public void addEtudiant(Etudiant etudiant) {etudiants.add(etudiant);}
    public void removeEtudiant(Etudiant etudiant) {etudiants.remove(etudiant);}

    public void setEnseignant(Enseignant enseignant) {this.enseignant = enseignant;}
    public Enseignant getEnseignant() {return this.enseignant;}
}

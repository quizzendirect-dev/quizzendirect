package fr.univ.angers.quizz.api.model;

import java.util.ArrayList;

public class Salon {

    private String id;
    private int etat; // 0 -> en attente | 1 -> en cours | 2 -> terminé
    private ArrayList<Question> questionsEnAttente;
    private Question questionCourante;
    private ArrayList<Question> questionsPosees;
    private int nbEtudiants;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public ArrayList<Question> getQuestionsEnAttente() {
        return questionsEnAttente;
    }

    public void setQuestionsEnAttente(ArrayList<Question> questionsEnAttente) {
        this.questionsEnAttente = questionsEnAttente;
    }

    public Question getQuestionCourante() {
        return questionCourante;
    }

    public void setQuestionCourante(Question questionCourante) {
        this.questionCourante = questionCourante;
    }

    public ArrayList<Question> getQuestionsPosees() {
        return questionsPosees;
    }

    public void setQuestionsPosees(ArrayList<Question> questionsPosees) {
        this.questionsPosees = questionsPosees;
    }

    public int getNbEtudiants() {
        return nbEtudiants;
    }

    public void setNbEtudiants(int nbEtudiants) {
        this.nbEtudiants = nbEtudiants;
    }
}

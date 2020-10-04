package fr.univangers.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Repertoire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_rep;
    private String nom;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_rep")
    private List<AffectationQuestion> affectationsQuestions = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rep")
    private List<AffectationEnseignant> affectationsEnseignants = new ArrayList<>();

    public Repertoire() {}
    public Repertoire(String nom){
        this.nom = nom;
    }

    public void setId_rep(int id_rep) {this.id_rep = id_rep;}
    public int getId_rep() {return id_rep;}

    public void setNom(String nom) {this.nom = nom;}
    public String getNom() {return nom;}

    public void setAffectationsQuestions(List<AffectationQuestion> affectationsQuestions) {this.affectationsQuestions = affectationsQuestions;}
    public List<AffectationQuestion> getAffectationsQuestions() {return affectationsQuestions;}

    public void setAffectationsEnseignants(List<AffectationEnseignant> affectationsEnseignants) {
        this.affectationsEnseignants = affectationsEnseignants;
    }
    public List<AffectationEnseignant> getAffectationsEnseignants() {
        return affectationsEnseignants;
    }
}
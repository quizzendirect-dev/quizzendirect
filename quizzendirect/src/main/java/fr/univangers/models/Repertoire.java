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
    private List<Question> questions = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rep")
    private Enseignant enseignant;

    public Repertoire() {}
    public Repertoire(String nom, Enseignant enseignant){
        this.enseignant = enseignant;
        this.nom = nom;
    }

    public void setId_rep(int id_rep) {this.id_rep = id_rep;}
    public int getId_rep() {return id_rep;}

    public void setNom(String nom) {this.nom = nom;}
    public String getNom() {return nom;}

    public void setAffectationsQuestions(List<Question> affectationsQuestions) {this.questions = questions;}
    public List<Question> getAffectationsQuestions() {return questions;}

    public void setEnseignant(Enseignant enseignant) {
        enseignant = enseignant;
    }
    public Enseignant getEnseignant() {
        return enseignant;
    }
}
package fr.univangers.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Enseignant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_ens;
    private String nom; //Nom + prénom
    private String mail;
    private String motdepasse;
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "id_ens")
    private List<AffectationEnseignant> affectations = new ArrayList<>();

    public Enseignant() {}
    public Enseignant(String nom, String mail, String motdepasse) {
        this.nom = nom;
        this.mail = mail;
        this.motdepasse = motdepasse;
    }

    public void setId_ens(int id_ens) {this.id_ens = id_ens;}
    public int getId_ens() {return id_ens;}

    public void setNom(String nom) {this.nom = nom;}
    public String getNom() {return nom;}

    public void setMail(String mail) {this.mail = mail;}
    public String getMail() {return mail;}

    public void setMotdepasse(String motdepasse) {this.motdepasse = motdepasse;}
    public String getMotdepasse() {return motdepasse;}

    public void setAffectations(List<AffectationEnseignant> affectations) {
        this.affectations = affectations;
    }
    public List<AffectationEnseignant> getAffectations() {
        return affectations;
    }
}
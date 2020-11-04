package fr.univangers.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Enseignant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_ens;
    private String prenom;
    private String nom;
    private String mail;
    private String motdepasse;
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "id_ens")
    private List<Repertoire> repertoires;

    public Enseignant() {}
    public Enseignant(String prenom,String nom, String mail, String motdepasse) {
        this.prenom=prenom;
        this.nom = nom;
        this.mail = mail;
        this.motdepasse = motdepasse;
    }

    public void setId_ens(int id_ens) {this.id_ens = id_ens;}
    public int getId_ens() {return id_ens;}

    public void setPrenom(String prenom) {this.prenom = prenom;}
    public String getPrenom() {return prenom;}

    public void setNom(String nom) {this.nom = nom;}
    public String getNom() {return nom;}

    public void setMail(String mail) {this.mail = mail;}
    public String getMail() {return mail;}

    public void setMotdepasse(String motdepasse) {this.motdepasse = motdepasse;}
    public String getMotdepasse() {return motdepasse;}

    public void setRepertoires(List<Repertoire> affectations) {
        this.repertoires = affectations;
    }
    public List<Repertoire> getRepertoires() {
        return repertoires;
    }
}
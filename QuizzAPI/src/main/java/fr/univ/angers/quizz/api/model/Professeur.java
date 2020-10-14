package fr.univ.angers.quizz.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;



@Data
@Entity
@Table(name = "PROFESSEUR")
public class Professeur implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_ens;
	private String nom; //Nom + prénom
	private String mail;
	private String motdepasse;
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinColumn(name = "id_ens")
	private List<Repertoire> repertoires;

	public Professeur() {}
	public Professeur(String nom, String mail, String motdepasse) {
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

	public void setRepertoires(List<Repertoire> affectations) {
		this.repertoires = affectations;
	}
	public List<Repertoire> getRepertoires() {
		return repertoires;
	}

		

	}
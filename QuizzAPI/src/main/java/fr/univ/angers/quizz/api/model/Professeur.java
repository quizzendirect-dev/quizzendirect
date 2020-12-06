package fr.univ.angers.quizz.api.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@Entity
@Table(name = "PROFESSEUR")
public class Professeur implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_prof;
	private String nom; //Nom + prénom
	private String mail;
	private String motdepasse;
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinColumn(name = "id_prof")
	private List<Repertoire> repertoires;

	public Professeur() {}
	public Professeur(String nom, String mail, String motdepasse) {
		this.nom = nom;
		this.mail = mail;
		this.motdepasse = motdepasse;
	}

	public void setId_prof(int id_prof) {this.id_prof = id_prof;}
	public int getId_prof() {return id_prof;}

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
	public void addRepertoire(Repertoire repertoire) {this.repertoires.add(repertoire);}
	public void removeRepertoire(Repertoire repertoire) {this.repertoires.remove(repertoire);}
}
package fr.univ.angers.quizz.api.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Data
@Entity
@Table(name = "QUESTION")
public class Question implements Serializable {
	public enum Choix {
		UNIQUE,
		MULTIPLE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_quest;
	private String intitule;
	private Choix type;
	@ElementCollection
	private List<String> reponsesBonnes;
	@ElementCollection
	private List<String> reponsesFausses;
	private int time; //En secondes
	@OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinColumn(name = "repertoire")
	private Repertoire repertoire;

	public Question() {}
	public Question(String intitule, Choix type, List<String> reponsesBonnes, List<String> reponsesFausses, int time){
		this.intitule = intitule;
		this.type = type;
		this.reponsesBonnes = reponsesBonnes;
		this.reponsesFausses = reponsesFausses;
		this.time = time;
	}

	public void setId_quest(int id_quest) {this.id_quest = id_quest;}
	public int getId_quest() {return id_quest;}

	public void setIntitule(String intitule) {this.intitule = intitule;}
	public String getIntitule() {return intitule;}

	public void setType(Choix type) {this.type = type;}
	public Choix getType() {return type;}

	public void setReponsesBonnes(List<String> reponsesBonnes) {this.reponsesBonnes = reponsesBonnes;}
	public List<String> getReponsesBonnes() {return reponsesBonnes;}

	public void setReponsesFausses(List<String> reponsesFausses) {this.reponsesFausses = reponsesFausses;}
	public List<String> getReponsesFausses() {return reponsesFausses;}

	public void setTime(int time) {this.time = time;}
	public int getTime() {return time;}

	public void setRepertoire(Repertoire repertoire) {
		this.repertoire = repertoire;
	}
	public Repertoire getRepertoire() {
		return repertoire;
	}

}

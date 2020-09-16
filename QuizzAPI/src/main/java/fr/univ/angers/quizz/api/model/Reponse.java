package fr.univ.angers.quizz.api.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Data
@Entity
@Table(name = "REPONSE")
public class Reponse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "ENONCER")
    private String enoncer;
    @Column(name = "VALID")
    private boolean valid;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEnoncer() {
		return enoncer;
	}
	public void setEnoncer(String enoncer) {
		this.enoncer = enoncer;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}

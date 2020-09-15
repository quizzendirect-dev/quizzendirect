package fr.univ.angers.quizz.api.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Reponse {
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

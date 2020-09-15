package fr.univ.angers.quizz.api.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

public class Question {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "ENONCER")
    private String enoncer;
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "reponse", cascade = CascadeType.ALL)
    @Column(name = "REPONSES")
    private List<Reponse> reponses;
    
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
	public List<Reponse> getReponses() {
		return reponses;
	}
	public void setReponses(List<Reponse> reponses) {
		this.reponses = reponses;
	}

}

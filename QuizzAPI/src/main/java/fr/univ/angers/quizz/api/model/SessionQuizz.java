package fr.univ.angers.quizz.api.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class SessionQuizz {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "NAME")
    private String enoncer;
    @Column(name = "REPONSES")
    private List<Reponse> reponses;
    @Column(name = "code")
    private String code;
    @Column(name = "time")
    private int time;
    
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
    
}

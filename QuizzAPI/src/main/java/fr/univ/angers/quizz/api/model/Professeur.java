package fr.univ.angers.quizz.api.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;



@Data
@Entity
@Table(name = "PROFESSEUR")
public class Professeur {

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long id;
	    @Column(name = "USER_NAME")
	    private String userName;
	    @Column(name = "time")
	    private Set<Question> questions;
	    
	    @OneToOne(fetch = FetchType.EAGER, mappedBy = "session", cascade = CascadeType.ALL)
	    private Set<SessionQuizz> sessions;
	    
	    public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public Set<Question> getQuestions() {
			return questions;
		}

		public void setQuestions(Set<Question> questions) {
			this.questions = questions;
		}

		public Set<SessionQuizz> getSessions() {
			return sessions;
		}

		public void setSessions(Set<SessionQuizz> sessions) {
			this.sessions = sessions;
		}

		

	}
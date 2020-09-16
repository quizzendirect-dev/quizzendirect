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
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long id;
	    @Column(name = "USER_NAME")
	    private String userName;
		@Column(name = "questions")
	    private QuestionList questions;

	    //@OneToOne(targetEntity=SessionQuizz.class, mappedBy = "sessions", fetch=FetchType.EAGER)
		@Column(name = "sessions")
	    private SessionQuizz sessions;
	    
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

		public QuestionList getQuestions() {
			return questions;
		}

		public void setQuestions(QuestionList questions) {
			this.questions = questions;
		}

		public SessionQuizz getSessions() {
			return sessions;
		}

		public void setSessions(SessionQuizz sessions) {
			this.sessions = sessions;
		}

		

	}
package fr.univ.angers.quizz.api.model;

public class Error {

    private String message;

    public Error(String message){
        this.message = message;
    }

    public void setMessage(String message) {this.message = message;}
    public String getMessage() {return this.message;}
}

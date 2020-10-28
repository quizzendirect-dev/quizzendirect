package fr.univ.angers.quizz.api.model;

public class Etudiant {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    private int id;
    private String pseudo;

}

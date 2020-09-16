package fr.univ.angers.quizz.api.model;

import java.io.Serializable;
import java.util.List;

public class ReponseList implements Serializable {
    private List<Reponse> reposes;

    public List<Reponse> getReposes() {
        return reposes;
    }

    public void setReposes(List<Reponse> reposes) {
        this.reposes = reposes;
    }
}

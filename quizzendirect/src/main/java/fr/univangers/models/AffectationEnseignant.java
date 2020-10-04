package fr.univangers.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "affectationenseignant")
@IdClass(AffectationEnseignantId.class)
public class AffectationEnseignant implements Serializable {

    @Id
    private int id_ens;
    @Id
    private int id_rep;

    public AffectationEnseignant(){}
    public AffectationEnseignant(int id_ens, int id_rep){
        this.id_ens = id_ens;
        this.id_rep = id_rep;
    }

    public void setId_ens(int id_ens) {
        this.id_ens = id_ens;
    }

    public int getId_ens() {
        return id_ens;
    }

    public void setId_rep(int id_rep) {
        this.id_rep = id_rep;
    }

    public int getId_rep() {
        return id_rep;
    }
}

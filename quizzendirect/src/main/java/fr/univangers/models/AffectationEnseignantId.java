package fr.univangers.models;

import java.io.Serializable;

public class AffectationEnseignantId implements Serializable {
    private int id_ens;
    private int id_rep;

    public AffectationEnseignantId(){}
    public AffectationEnseignantId(int id_ens, int id_rep){
        this.id_ens = id_ens;
        this.id_rep = id_rep;
    }
}

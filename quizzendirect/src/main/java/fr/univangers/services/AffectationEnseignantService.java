package fr.univangers.services;

import fr.univangers.models.AffectationEnseignant;
import fr.univangers.models.AffectationEnseignantId;
import fr.univangers.repositories.AffectationEnseignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AffectationEnseignantService {

    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private RepertoireService repertoireService;
    @Autowired
    private AffectationEnseignantRepository affectationEnseignantRepository;

    public List<AffectationEnseignant> getAffectationRepertoire(int id_rep){
        List<AffectationEnseignant> affectations = new ArrayList<>();
        for(AffectationEnseignant affectation : affectationEnseignantRepository.findAll()){
            if(affectation.getId_rep() == id_rep) affectations.add(affectation);
        }
        return affectations;
    }

    public List<AffectationEnseignant> getAffectationEnseignant(int id_ens){
        List<AffectationEnseignant> affectations = new ArrayList<>();
        for(AffectationEnseignant affectation : affectationEnseignantRepository.findAll()){
            if(affectation.getId_ens() == id_ens) affectations.add(affectation);
        }
        return affectations;
    }

    public void addAffectation(int id_ens, int id_rep){
        Optional<AffectationEnseignant> affectation = affectationEnseignantRepository.findById(new AffectationEnseignantId(id_ens, id_rep));
        if(!affectation.isPresent() && getAffectationRepertoire(id_rep).isEmpty()) {
            AffectationEnseignant newAffectation = new AffectationEnseignant(id_ens, id_rep);
            affectationEnseignantRepository.save(newAffectation);
        }
    }

    public void removeAffectation(int id_ens, int id_rep){
        Optional<AffectationEnseignant> affectation = affectationEnseignantRepository.findById(new AffectationEnseignantId(id_ens, id_rep));
        if(affectation.isPresent()){
            affectationEnseignantRepository.delete(affectation.get());
            repertoireService.removeRepertoire(id_rep);
        }
    }
}

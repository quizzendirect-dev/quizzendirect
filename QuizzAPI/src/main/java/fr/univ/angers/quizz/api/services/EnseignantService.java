package fr.univ.angers.quizz.api.services;

import fr.univ.angers.quizz.api.model.Professeur;
import fr.univ.angers.quizz.api.repository.ProfesseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EnseignantService {

    @Autowired
    private ProfesseurRepository enseignantRepository;

    public ArrayList<Professeur> getAllEnseignants(){
        ArrayList<Professeur> enseignants = new ArrayList<>();
        enseignantRepository.findAll().forEach(enseignants::add);
        return enseignants;
    }

    public Professeur getEnseignant(int id_ens) {
        return enseignantRepository.findById(id_ens).orElse(new Professeur());
    }

    public void addEnseignant(Professeur enseignant){
        enseignantRepository.save(enseignant);
    }

    public void updateEnseignant(Professeur enseignant){
        enseignantRepository.save(enseignant);
    }

    public void removeEnseignant(int id_ens){
        enseignantRepository.deleteById(id_ens);
    }
}

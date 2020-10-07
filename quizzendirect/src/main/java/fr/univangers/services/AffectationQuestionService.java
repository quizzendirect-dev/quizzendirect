package fr.univangers.services;

import fr.univangers.models.*;
import fr.univangers.repositories.AffectationQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AffectationQuestionService {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private RepertoireService repertoireService;
    @Autowired
    private AffectationQuestionRepository affectationQuestionRepository;

    public ArrayList<AffectationQuestion> getAllAffectations(){
        ArrayList<AffectationQuestion> affectations = new ArrayList<>();
        affectationQuestionRepository.findAll().forEach(affectations::add);
        return affectations;
    }

    public void addAffectation(int id_quest, int id_rep){
        Optional<AffectationQuestion> affectation = affectationQuestionRepository.findById(new AffectationQuestionId(id_quest, id_rep));
        if(!affectation.isPresent()) {
            AffectationQuestion newAffectation = new AffectationQuestion(id_quest, id_rep);
            affectationQuestionRepository.save(newAffectation);
        }
    }

    public void removeAffectation(int id_quest, int id_rep){
        Optional<AffectationQuestion> affectation = affectationQuestionRepository.findById(new AffectationQuestionId(id_quest, id_rep));
        if(affectation.isPresent()) {
            affectationQuestionRepository.delete(affectation.get());
            boolean repertoireVide = true;
            for(AffectationQuestion aff : getAllAffectations()){
                if(aff.getId_rep() == id_rep){
                    repertoireVide = false;
                    break;
                }
            }
            if(repertoireVide){
                repertoireService.removeRepertoire(id_rep);
            }
        }
    }
}

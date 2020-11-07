package fr.univ.angers.quizz.api.datasfetchers;

import fr.univ.angers.quizz.api.model.Professeur;
import fr.univ.angers.quizz.api.repository.ProfesseurRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;

import io.reactivex.Observable;

public class PublisheDatafetcher {

    @Autowired
    private ProfesseurRepository professeurRepository;

/*    public DataFetcher<Professeur> publishProfesseurs(){
        return DataFetcher
    }*/
}

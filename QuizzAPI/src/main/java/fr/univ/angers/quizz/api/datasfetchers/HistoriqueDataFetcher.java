package fr.univ.angers.quizz.api.datasfetchers;

import fr.univ.angers.quizz.api.model.Historique;
import fr.univ.angers.quizz.api.model.Professeur;
import fr.univ.angers.quizz.api.model.Repertoire;
import fr.univ.angers.quizz.api.repository.HistoriqueRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HistoriqueDataFetcher {
    @Autowired
    private HistoriqueRepository historiqueRepository;

    public DataFetcher<List<Historique>> getAllProduct(){
        return dataFetchingEnvironment -> historiqueRepository.findAll();
    }

    public DataFetcher<Historique> getHistoriqueByDate(){
        return dataFetchingEnvironment -> historiqueRepository.findByDate(dataFetchingEnvironment.getArgument("date"));
    }

    public DataFetcher<Historique> createHistorique(){
        return dataFetchingEnvironment -> {
            Historique nouveauHistorique = new Historique();
            nouveauHistorique.setId_quest(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")));
            nouveauHistorique.setDate(dataFetchingEnvironment.getArgument("date"));
            historiqueRepository.save(nouveauHistorique);
            return nouveauHistorique;
        };
    }

    public DataFetcher<Historique> updateDate(){
        return dataFetchingEnvironment -> {
            Historique historique = historiqueRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest"))).orElse(new Historique());
            historique.setDate(dataFetchingEnvironment.getArgument("date"));
            historiqueRepository.save(historique);
            return historique;
        };
    }

    public DataFetcher<Historique> removeHistorique(){
        return dataFetchingEnvironment -> {
            Historique historique = historiqueRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest"))).orElse(new Historique());
            historiqueRepository.delete(historique);
            return historique;
        };
    }
}

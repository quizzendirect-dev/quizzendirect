package fr.univ.angers.quizz.api.datasfetchers;

import fr.univ.angers.quizz.api.model.Enseignant;
import fr.univ.angers.quizz.api.model.Historique;
import fr.univ.angers.quizz.api.model.Question;
import fr.univ.angers.quizz.api.repository.HistoriqueRepository;
import fr.univ.angers.quizz.api.repository.QuestionRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class HistoriqueDataFetcher {
    @Autowired
    private HistoriqueRepository historiqueRepository;
    @Autowired
    private QuestionRepository questionRepository;

    public DataFetcher<List<Historique>> getAllProduct(){
        return dataFetchingEnvironment -> historiqueRepository.findAll();
    }

    public DataFetcher<Object> getHistoriqueById(){
        return dataFetchingEnvironment -> {
            Optional<Historique> historique = historiqueRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_hist")));
            if(historique.isPresent()) return historique.get();
            return new Error("Erreur : Cet historique n'existe pas.");
        };
    }

    public DataFetcher<Object> createHistorique(){
        return dataFetchingEnvironment -> {
            List<Historique> historiques = historiqueRepository.findAll();
            for(Historique historique : historiques) {
                if (Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")) == historique.getQuestion().getId_quest() && dataFetchingEnvironment.getArgument("date").equals(historique.getDate())) {
                    return new Error("Erreur : Cet historique existe déjà.");
                }
            }

            Optional<Question> question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")));
            if(!question.isPresent()) return new Error("Erreur : Cette question n'existe pas.");

            Historique nouveauHistorique = new Historique();
            nouveauHistorique.setQuestion(question.get());
            nouveauHistorique.setDate(dataFetchingEnvironment.getArgument("date"));
            historiqueRepository.save(nouveauHistorique);
            return nouveauHistorique;
        };
    }

    public DataFetcher<Object> updateQuestion(){
        return dataFetchingEnvironment -> {
            Optional<Historique> historique = historiqueRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_hist")));
            if(!historique.isPresent()) return new Error("Erreur : Cet historique n'existe pas.");

            Optional<Question> question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")));
            if(!question.isPresent()) return new Error("Erreur : Cette question n'existe pas.");

            historique.get().setQuestion(question.get());
            historiqueRepository.save(historique.get());
            return historique;
        };
    }

    public DataFetcher<Object> updateDate(){
        return dataFetchingEnvironment -> {
            Optional<Historique> historique = historiqueRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_hist")));
            if(!historique.isPresent()) return new Error("Erreur : Cet historique n'existe pas.");

            historique.get().setDate(dataFetchingEnvironment.getArgument("date"));
            historiqueRepository.save(historique.get());
            return historique;
        };
    }

    public DataFetcher<Object> removeHistorique(){
        return dataFetchingEnvironment -> {
            Optional<Historique> historique = historiqueRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_hist")));
            if(!historique.isPresent()) return new Error("Erreur : Cet historique n'existe pas.");

            historiqueRepository.delete(historique.get());
            return historique;
        };
    }
}

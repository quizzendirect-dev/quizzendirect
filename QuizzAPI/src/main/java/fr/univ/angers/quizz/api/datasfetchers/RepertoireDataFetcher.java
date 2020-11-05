package fr.univ.angers.quizz.api.datasfetchers;

import fr.univ.angers.quizz.api.model.Enseignant;
import fr.univ.angers.quizz.api.model.Question;
import fr.univ.angers.quizz.api.model.Repertoire;
import fr.univ.angers.quizz.api.repository.EnseignantRepository;
import fr.univ.angers.quizz.api.repository.QuestionRepository;
import fr.univ.angers.quizz.api.repository.RepertoireRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RepertoireDataFetcher {

    @Autowired
    private RepertoireRepository repertoireRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;

    public DataFetcher<List<Repertoire>> getAllProduct(){
        return dataFetchingEnvironment -> repertoireRepository.findAll();
    }

    public DataFetcher<Object> getRepertoireById(){
        return dataFetchingEnvironment -> {
            Optional<Repertoire> repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep")));
            if(repertoire.isPresent()) return repertoire.get();
            return new Error("Erreur : Ce répertoire n'existe pas.");
        };
    }

    public DataFetcher<Object> createRepertoire(){
        return dataFetchingEnvironment -> {
            List<Repertoire> repertoires = repertoireRepository.findAll();
            for(Repertoire repertoire : repertoires) {
                if (dataFetchingEnvironment.getArgument("nom").equals(repertoire.getNom()) && Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")) == repertoire.getEnseignant().getId_ens()) {
                    return new Error("Erreur : Ce répertoire existe déjà.");
                }
            }

            Optional<Enseignant> enseignant = enseignantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")));
            if(!enseignant.isPresent()) return new Error("Erreur : Cet enseignant n'existe pas.");

            Repertoire nouveauRepertoire = new Repertoire();
            nouveauRepertoire.setNom(dataFetchingEnvironment.getArgument("nom"));
            nouveauRepertoire.setEnseignant(enseignant.get());
            repertoireRepository.save(nouveauRepertoire);
            return nouveauRepertoire;
        };
    }

    public DataFetcher<Object> updateNom(){
        return dataFetchingEnvironment -> {
            Optional<Repertoire> repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep")));
            if(!repertoire.isPresent()) return new Error("Erreur : Ce répertoire n'existe pas.");

            repertoire.get().setNom(dataFetchingEnvironment.getArgument("nom"));
            repertoireRepository.save(repertoire.get());
            return repertoire;
        };
    }

    public DataFetcher<Object> ajouterQuestion(){
        return dataFetchingEnvironment -> {
            Optional<Repertoire> repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep")));
            if(!repertoire.isPresent()) return new Error("Erreur : Ce répertoire n'existe pas.");

            Optional<Question> question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")));
            if(!question.isPresent()) return new Error("Erreur : Cette question n'existe pas.");

            repertoire.get().addQuestion(question.get());
            repertoireRepository.save(repertoire.get());
            return repertoire;
        };
    }

    public DataFetcher<Object> supprimerQuestion(){
        return dataFetchingEnvironment -> {
            Optional<Repertoire> repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep")));
            if(!repertoire.isPresent()) return new Error("Erreur : Ce répertoire n'existe pas.");

            Optional<Question> question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")));
            if(!question.isPresent()) return new Error("Erreur : Cette question n'existe pas.");

            repertoire.get().removeQuestion(question.get());
            repertoireRepository.save(repertoire.get());
            return repertoire;
        };
    }

    public DataFetcher<Object> updateEnseignant(){
        return dataFetchingEnvironment -> {
            Optional<Repertoire> repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep")));
            if(!repertoire.isPresent()) return new Error("Erreur : Ce répertoire n'existe pas.");

            Optional<Enseignant> enseignant = enseignantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")));
            if(!enseignant.isPresent()) return new Error("Erreur : Cet enseignant n'existe pas.");

            repertoire.get().setEnseignant(enseignant.get());
            repertoireRepository.save(repertoire.get());
            return repertoire;
        };
    }

    public DataFetcher<Object> removeRepertoire(){
        return dataFetchingEnvironment -> {
            Optional<Repertoire> repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep")));
            if(!repertoire.isPresent()) return new Error("Erreur : Ce répertoire n'existe pas.");

            repertoireRepository.delete(repertoire.get());
            return repertoire;
        };
    }
}

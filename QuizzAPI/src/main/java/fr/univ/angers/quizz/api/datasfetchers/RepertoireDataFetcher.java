package fr.univ.angers.quizz.api.datasfetchers;

import fr.univ.angers.quizz.api.model.Professeur;
import fr.univ.angers.quizz.api.model.Question;
import fr.univ.angers.quizz.api.model.Repertoire;
import fr.univ.angers.quizz.api.repository.ProfesseurRepository;
import fr.univ.angers.quizz.api.repository.QuestionRepository;
import fr.univ.angers.quizz.api.repository.RepertoireRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepertoireDataFetcher {
    @Autowired
    private RepertoireRepository repertoireRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ProfesseurRepository professeurRepository;

    public DataFetcher<List<Repertoire>> getAllProduct(){
        return dataFetchingEnvironment -> repertoireRepository.findAll();
    }

    public DataFetcher<Repertoire> getRepertoireByNom(){
        return dataFetchingEnvironment -> repertoireRepository.findByNom(dataFetchingEnvironment.getArgument("nom"));
    }

    public DataFetcher<Repertoire> createRepertoire(){
        return dataFetchingEnvironment -> {
            Repertoire nouveauRepertoire = new Repertoire();
            nouveauRepertoire.setNom(dataFetchingEnvironment.getArgument("nom"));
            repertoireRepository.save(nouveauRepertoire);
            return nouveauRepertoire;
        };
    }

    public DataFetcher<Repertoire> updateNom(){
        return dataFetchingEnvironment -> {
            Repertoire repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep"))).orElse(new Repertoire());
            repertoire.setNom(dataFetchingEnvironment.getArgument("nom"));
            repertoireRepository.save(repertoire);
            return repertoire;
        };
    }

    public DataFetcher<Repertoire> ajouterQuestion(){
        return dataFetchingEnvironment -> {
            Repertoire repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep"))).orElse(new Repertoire());
            Question question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest"))).orElse(new Question());
            repertoire.addQuestion(question);
            repertoireRepository.save(repertoire);
            return repertoire;
        };
    }

    public DataFetcher<Repertoire> supprimerQuestion(){
        return dataFetchingEnvironment -> {
            Repertoire repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep"))).orElse(new Repertoire());
            Question question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest"))).orElse(new Question());
            repertoire.removeQuestion(question);
            repertoireRepository.save(repertoire);
            return repertoire;
        };
    }

    public DataFetcher<Repertoire> updateProfesseur(){
        return dataFetchingEnvironment -> {
            Repertoire repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep"))).orElse(new Repertoire());
            Professeur professeur = professeurRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_prof"))).orElse(new Professeur());
            repertoire.setProfesseur(professeur);
            repertoireRepository.save(repertoire);
            return repertoire;
        };
    }

    public DataFetcher<Repertoire> removeRepertoire(){
        return dataFetchingEnvironment -> {
            Repertoire repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep"))).orElse(new Repertoire());
            repertoireRepository.delete(repertoire);
            return repertoire;
        };
    }
}

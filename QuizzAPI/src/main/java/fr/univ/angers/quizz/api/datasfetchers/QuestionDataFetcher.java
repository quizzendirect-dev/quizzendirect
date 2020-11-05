package fr.univ.angers.quizz.api.datasfetchers;

import fr.univ.angers.quizz.api.model.Enseignant;
import fr.univ.angers.quizz.api.model.Question;
import fr.univ.angers.quizz.api.model.Repertoire;
import fr.univ.angers.quizz.api.repository.QuestionRepository;
import fr.univ.angers.quizz.api.repository.RepertoireRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class QuestionDataFetcher {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private RepertoireRepository repertoireRepository;

    public DataFetcher<List<Question>> getAllProduct(){
        return dataFetchingEnvironment -> questionRepository.findAll();
    }

    public DataFetcher<Object> getQuestionById(){
        return dataFetchingEnvironment -> {
            Optional<Question> question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")));
            if(question.isPresent()) return question.get();
            return new Error("Erreur : Cette question n'existe pas.");
        };
    }

    public DataFetcher<Object> createQuestion(){
        return dataFetchingEnvironment -> {
            List<Question> questions = questionRepository.findAll();
            for(Question question : questions) {
                if (dataFetchingEnvironment.getArgument("intitule").equals(question.getIntitule()) && dataFetchingEnvironment.getArgument("choixUnique").equals(question.isChoixUnique()) && dataFetchingEnvironment.getArgument("reponsesBonnes").equals(question.getReponsesBonnes()) && dataFetchingEnvironment.getArgument("reponsesFausses").equals(question.getReponsesFausses()) && dataFetchingEnvironment.getArgument("time").equals(question.getTime())) {
                    return new Error("Erreur : Cette question existe déjà.");
                }
            }
            Question nouvelleQuestion = new Question();
            nouvelleQuestion.setIntitule(dataFetchingEnvironment.getArgument("intitule"));
            nouvelleQuestion.setChoixUnique(dataFetchingEnvironment.getArgument("choixUnique"));
            nouvelleQuestion.setReponsesBonnes(dataFetchingEnvironment.getArgument("reponsesBonnes"));
            nouvelleQuestion.setReponsesFausses(dataFetchingEnvironment.getArgument("reponsesFausses"));
            nouvelleQuestion.setTime(dataFetchingEnvironment.getArgument("time"));
            questionRepository.save(nouvelleQuestion);
            return nouvelleQuestion;
        };
    }

    public DataFetcher<Object> updateIntitule(){
        return dataFetchingEnvironment -> {
            Optional<Question> question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")));
            if(!question.isPresent()) return new Error("Erreur : Cette question n'existe pas.");

            question.get().setIntitule(dataFetchingEnvironment.getArgument("intitule"));
            questionRepository.save(question.get());
            return question;
        };
    }

    public DataFetcher<Object> updateChoixUnique(){
        return dataFetchingEnvironment -> {
            Optional<Question> question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")));
            if(!question.isPresent()) return new Error("Erreur : Cette question n'existe pas.");

            question.get().setChoixUnique(dataFetchingEnvironment.getArgument("choixUnique"));
            questionRepository.save(question.get());
            return question;
        };
    }

    public DataFetcher<Object> updateReponsesBonnes(){
        return dataFetchingEnvironment -> {
            Optional<Question> question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")));
            if(!question.isPresent()) return new Error("Erreur : Cette question n'existe pas.");

            question.get().setReponsesBonnes(dataFetchingEnvironment.getArgument("reponsesBonnes"));
            questionRepository.save(question.get());
            return question;
        };
    }

    public DataFetcher<Object> updateReponsesFausses(){
        return dataFetchingEnvironment -> {
            Optional<Question> question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")));
            if(!question.isPresent()) return new Error("Erreur : Cette question n'existe pas.");

            question.get().setReponsesFausses(dataFetchingEnvironment.getArgument("reponsesFausses"));
            questionRepository.save(question.get());
            return question;
        };
    }

    public DataFetcher<Object> updateTime(){
        return dataFetchingEnvironment -> {
            Optional<Question> question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")));
            if(!question.isPresent()) return new Error("Erreur : Cette question n'existe pas.");

            question.get().setTime(dataFetchingEnvironment.getArgument("time"));
            questionRepository.save(question.get());
            return question;
        };
    }

    public DataFetcher<Object> updateRepertoire(){
        return dataFetchingEnvironment -> {
            Optional<Question> question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")));
            if(!question.isPresent()) return new Error("Erreur : Cette question n'existe pas.");

            Optional<Repertoire> repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep")));
            if(!repertoire.isPresent()) return new Error("Erreur : Ce répertoire n'existe pas.");

            question.get().setRepertoire(repertoire.get());
            questionRepository.save(question.get());
            return question;
        };
    }

    public DataFetcher<Object> removeQuestion(){
        return dataFetchingEnvironment -> {
            Optional<Question> question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")));
            if(!question.isPresent()) return new Error("Erreur : Cette question n'existe pas.");

            questionRepository.delete(question.get());
            return question;
        };
    }
}

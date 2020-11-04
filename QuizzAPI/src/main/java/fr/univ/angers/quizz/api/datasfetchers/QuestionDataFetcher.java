package fr.univ.angers.quizz.api.datasfetchers;

import fr.univ.angers.quizz.api.model.Professeur;
import fr.univ.angers.quizz.api.model.Question;
import fr.univ.angers.quizz.api.model.Repertoire;
import fr.univ.angers.quizz.api.repository.QuestionRepository;
import fr.univ.angers.quizz.api.repository.RepertoireRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionDataFetcher {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private RepertoireRepository repertoireRepository;

    public DataFetcher<List<Question>> getAllProduct(){
        return dataFetchingEnvironment -> questionRepository.findAll();
    }

    public DataFetcher<Question> getQuestionByIntitule(){
        return dataFetchingEnvironment -> questionRepository.findByIntitule(dataFetchingEnvironment.getArgument("intitule"));
    }

    public DataFetcher<Question> createQuestion(){
        return dataFetchingEnvironment -> {
            Question nouvelleQuestion = new Question();
            nouvelleQuestion.setIntitule(dataFetchingEnvironment.getArgument("intitule"));
            nouvelleQuestion.setChoixUnique(dataFetchingEnvironment.getArgument("choixUnique"));
            nouvelleQuestion.setTime(dataFetchingEnvironment.getArgument("time"));
            questionRepository.save(nouvelleQuestion);
            return nouvelleQuestion;
        };
    }

    public DataFetcher<Question> updateIntitule(){
        return dataFetchingEnvironment -> {
            Question question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest"))).orElse(new Question());
            question.setIntitule(dataFetchingEnvironment.getArgument("intitule"));
            questionRepository.save(question);
            return question;
        };
    }

    public DataFetcher<Question> updateChoixUnique(){
        return dataFetchingEnvironment -> {
            Question question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest"))).orElse(new Question());
            question.setChoixUnique(dataFetchingEnvironment.getArgument("choixUnique"));
            questionRepository.save(question);
            return question;
        };
    }

    public DataFetcher<Question> updateReponsesBonnes(){
        return dataFetchingEnvironment -> {
            Question question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest"))).orElse(new Question());
            question.setReponsesBonnes(dataFetchingEnvironment.getArgument("reponsesBonnes"));
            questionRepository.save(question);
            return question;
        };
    }

    public DataFetcher<Question> updateReponsesFausses(){
        return dataFetchingEnvironment -> {
            Question question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest"))).orElse(new Question());
            question.setReponsesFausses(dataFetchingEnvironment.getArgument("reponsesFausses"));
            questionRepository.save(question);
            return question;
        };
    }

    public DataFetcher<Question> updateTime(){
        return dataFetchingEnvironment -> {
            Question question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest"))).orElse(new Question());
            question.setTime(dataFetchingEnvironment.getArgument("time"));
            questionRepository.save(question);
            return question;
        };
    }

    public DataFetcher<Question> updateRepertoire(){
        return dataFetchingEnvironment -> {
            Question question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest"))).orElse(new Question());
            Repertoire repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep"))).orElse(new Repertoire());
            question.setRepertoire(repertoire);
            questionRepository.save(question);
            return question;
        };
    }

    public DataFetcher<Question> removeQuestion(){
        return dataFetchingEnvironment -> {
            Question question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest"))).orElse(new Question());
            questionRepository.delete(question);
            return question;
        };
    }
}

package fr.univ.angers.quizz.api.datasfetchers;

import fr.univ.angers.quizz.api.model.Question;
import fr.univ.angers.quizz.api.repository.QuestionRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionDataFetcher {

    @Autowired
    private QuestionRepository questionRepository;

    public DataFetcher<List<Question>> getAllProduct(){
        return dataFetchingEnvironment -> questionRepository.findAll();
    }

    public DataFetcher<Question> getQuestionByEnoncer(){
        return dataFetchingEnvironment -> questionRepository.findByIntitule(dataFetchingEnvironment.getArgument("enoncer"));
    }
}

package fr.univ.angers.quizz.api.datasfetchers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import fr.univ.angers.quizz.api.model.Professeur;
import fr.univ.angers.quizz.api.repository.ProfesseurRepository;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

@Component
public class ProfesseurDataFetcher {
	
	 @Autowired
	 private ProfesseurRepository professeurRepository;
	  

	  
	  
	  public DataFetcher<List<Professeur>> getAllProduct(){
	      return dataFetchingEnvironment -> professeurRepository.findAll();
	    }
	  
	  public DataFetcher<Professeur> getProfesseurByName(){
	      return dataFetchingEnvironment -> professeurRepository.findByUserName(dataFetchingEnvironment.getArgument("userName"));
	    }

}

package fr.univ.angers.quizz.api.datasfetchers;

import java.util.ArrayList;
import java.util.List;

import fr.univ.angers.quizz.api.model.Historique;
import fr.univ.angers.quizz.api.model.Repertoire;
import fr.univ.angers.quizz.api.repository.RepertoireRepository;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;


import fr.univ.angers.quizz.api.model.Professeur;
import fr.univ.angers.quizz.api.repository.ProfesseurRepository;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

@Component
public class ProfesseurDataFetcher {
	
	 @Autowired
	 private ProfesseurRepository professeurRepository;
	 @Autowired
	 private RepertoireRepository repertoireRepository;



	 public DataFetcher<List<Professeur>> getAllProfesseurs(){
	    return dataFetchingEnvironment -> professeurRepository.findAll();
	 }

	 public DataFetcher<Professeur> getProfesseurByNom(){
	 	return dataFetchingEnvironment -> professeurRepository.findByNom(dataFetchingEnvironment.getArgument("nom"));
	 }

	 public DataFetcher<Professeur> createProfesseur(){
	 	return dataFetchingEnvironment -> {
	 		Professeur nouveauProfesseur = new Professeur();
			nouveauProfesseur.setNom(dataFetchingEnvironment.getArgument("nom"));
			nouveauProfesseur.setMail(dataFetchingEnvironment.getArgument("mail"));
			nouveauProfesseur.setMotdepasse(dataFetchingEnvironment.getArgument("motdepasse"));
	 		professeurRepository.save(nouveauProfesseur);
			return nouveauProfesseur;
	 	};
	 }

	 public DataFetcher<Professeur> updateNom(){
	 	return dataFetchingEnvironment -> {
			Professeur professeur = professeurRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_prof"))).orElse(new Professeur());
			professeur.setNom(dataFetchingEnvironment.getArgument("nom"));
			professeurRepository.save(professeur);
			return professeur;
		};
	 }

	public DataFetcher<Professeur> updateMail(){
		return dataFetchingEnvironment -> {
			Professeur professeur = professeurRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_prof"))).orElse(new Professeur());
			professeur.setMail(dataFetchingEnvironment.getArgument("mail"));
			professeurRepository.save(professeur);
			return professeur;
		};
	}

	public DataFetcher<Professeur> updateMotDePasse(){
		return dataFetchingEnvironment -> {
			Professeur professeur = professeurRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_prof"))).orElse(new Professeur());
			professeur.setMotdepasse(dataFetchingEnvironment.getArgument("motdepasse"));
			professeurRepository.save(professeur);
			return professeur;
		};
	}

	public DataFetcher<Professeur> ajouterRepertoire(){
		return dataFetchingEnvironment -> {
			Professeur professeur = professeurRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_prof"))).orElse(new Professeur());
			Repertoire repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep"))).orElse(new Repertoire());
			professeur.addRepertoire(repertoire);
			professeurRepository.save(professeur);
			return professeur;
		};
	}

	public DataFetcher<Professeur> supprimerRepertoire(){
		return dataFetchingEnvironment -> {
			Professeur professeur = professeurRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_prof"))).orElse(new Professeur());
			Repertoire repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep"))).orElse(new Repertoire());
			professeur.removeRepertoire(repertoire);
			professeurRepository.save(professeur);
			return professeur;
		};
	}

	public DataFetcher<Professeur> removeProfesseur(){
		return dataFetchingEnvironment -> {
			Professeur professeur = professeurRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_prof"))).orElse(new Professeur());
			professeurRepository.delete(professeur);
			return professeur;
		};
	}
}

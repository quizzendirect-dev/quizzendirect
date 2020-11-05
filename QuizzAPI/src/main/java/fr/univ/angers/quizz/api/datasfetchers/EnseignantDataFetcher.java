package fr.univ.angers.quizz.api.datasfetchers;

import fr.univ.angers.quizz.api.model.Enseignant;
import fr.univ.angers.quizz.api.model.Repertoire;
import fr.univ.angers.quizz.api.repository.EnseignantRepository;
import fr.univ.angers.quizz.api.repository.RepertoireRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EnseignantDataFetcher {
	
	 @Autowired
	 private EnseignantRepository enseignantRepository;
	 @Autowired
	 private RepertoireRepository repertoireRepository;

	 public DataFetcher<List<Enseignant>> getAllProduct(){
	    return dataFetchingEnvironment -> enseignantRepository.findAll();
	 }
	  
	 public DataFetcher<Object> getEnseignantById(){
	 	return dataFetchingEnvironment -> {
			Optional<Enseignant> enseignant = enseignantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")));
			if(enseignant.isPresent()) return enseignant.get();
			return new Error("Error : Cet enseignant n'existe pas.");
		};
	 }

	 public DataFetcher<Object> createEnseignant(){
	 	return dataFetchingEnvironment -> {
			List<Enseignant> enseignants = enseignantRepository.findAll();
			for(Enseignant enseignant : enseignants){
				if(dataFetchingEnvironment.getArgument("nom").equals(enseignant.getNom()) && dataFetchingEnvironment.getArgument("mail").equals(enseignant.getMail())){
					return new Error("Erreur : Cet enseignant existe déjà.");
				}
			}
			Enseignant nouvelEnseignant = new Enseignant();
			nouvelEnseignant.setNom(dataFetchingEnvironment.getArgument("nom"));
			nouvelEnseignant.setMail(dataFetchingEnvironment.getArgument("mail"));
			nouvelEnseignant.setMotdepasse(dataFetchingEnvironment.getArgument("motdepasse"));
			enseignantRepository.save(nouvelEnseignant);
			return nouvelEnseignant;
	 	};
	 }

	 public DataFetcher<Object> updateNom(){
	 	return dataFetchingEnvironment -> {
			Optional<Enseignant> enseignant = enseignantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")));
			if(!enseignant.isPresent()) return new Error("Erreur : Cet enseignant n'existe pas.");

			enseignant.get().setNom(dataFetchingEnvironment.getArgument("nom"));
			enseignantRepository.save(enseignant.get());
			return enseignant;
		};
	 }

	public DataFetcher<Object> updateMail(){
		return dataFetchingEnvironment -> {
			Optional<Enseignant> enseignant = enseignantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")));
			if(!enseignant.isPresent()) return new Error("Erreur : Cet enseignant n'existe pas.");

			enseignant.get().setMail(dataFetchingEnvironment.getArgument("mail"));
			enseignantRepository.save(enseignant.get());
			return enseignant;
		};
	}

	public DataFetcher<Object> updateMotDePasse(){
		return dataFetchingEnvironment -> {
			Optional<Enseignant> enseignant = enseignantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")));
			if(!enseignant.isPresent()) return new Error("Erreur : Cet enseignant n'existe pas.");

			enseignant.get().setMotdepasse(dataFetchingEnvironment.getArgument("motdepasse"));
			enseignantRepository.save(enseignant.get());
			return enseignant;
		};
	}

	public DataFetcher<Object> ajouterRepertoire(){
		return dataFetchingEnvironment -> {
			Optional<Enseignant> enseignant = enseignantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")));
			if(!enseignant.isPresent()) return new Error("Erreur : Cet enseignant n'existe pas.");

			Optional<Repertoire> repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep")));
			if(!repertoire.isPresent()) return new Error("Erreur : Ce répertoire n'existe pas.");

			enseignant.get().addRepertoire(repertoire.get());
			enseignantRepository.save(enseignant.get());
			return enseignant;
		};
	}

	public DataFetcher<Object> supprimerRepertoire(){
		return dataFetchingEnvironment -> {
			Optional<Enseignant> enseignant = enseignantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")));
			if(!enseignant.isPresent()) return new Error("Erreur : Cet enseignant n'existe pas.");

			Optional<Repertoire> repertoire = repertoireRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_rep")));
			if(!repertoire.isPresent()) return new Error("Erreur : Ce répertoire n'existe pas.");

			enseignant.get().removeRepertoire(repertoire.get());
			enseignantRepository.save(enseignant.get());
			return enseignant;
		};
	}

	public DataFetcher<Object> removeEnseignant(){
		return dataFetchingEnvironment -> {
			Optional<Enseignant> enseignant = enseignantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")));
			if(!enseignant.isPresent()) return new Error("Erreur : Cet enseignant n'existe pas.");

			enseignantRepository.delete(enseignant.get());
			return enseignant;
		};
	}
}

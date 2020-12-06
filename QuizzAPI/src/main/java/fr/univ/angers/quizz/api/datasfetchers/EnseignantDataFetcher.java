package fr.univ.angers.quizz.api.datasfetchers;

import fr.univ.angers.quizz.api.model.Enseignant;
import fr.univ.angers.quizz.api.model.Error;
import fr.univ.angers.quizz.api.model.Repertoire;
import fr.univ.angers.quizz.api.repository.EnseignantRepository;
import fr.univ.angers.quizz.api.repository.RepertoireRepository;
import graphql.schema.DataFetcher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
			// Enseignant non trouvé
			return new Error("getEnseignantById", "NOT_FOUND", "Erreur : Aucun enseignant correspondant à l'ID : '" + Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")) +  "' n'a été trouvé.");
		};
	 }

	 public DataFetcher<Object> createEnseignant(){
	 	return dataFetchingEnvironment -> {
			// On vérifie que toutes les données passées en paramètres sont valides
			if(StringUtils.normalizeSpace(dataFetchingEnvironment.getArgument("nom")).length() <= 0) return new Error("createEnseignant", "INVALID_ARG", "Erreur : Le nom de l'enseignant que vous avez saisi : '" + dataFetchingEnvironment.getArgument("nom") +  "' n'est pas correct.");
			if(StringUtils.normalizeSpace(dataFetchingEnvironment.getArgument("mail")).length() <= 0) return new Error("createEnseignant", "INVALID_ARG", "Erreur : L'adresse mail de l'enseignant que vous avez saisie : '" + dataFetchingEnvironment.getArgument("mail") +  "' n'est pas correcte.");
			if(StringUtils.normalizeSpace(dataFetchingEnvironment.getArgument("motdepasse")).length() <= 0) return new Error("createEnseignant", "INVALID_ARG", "Erreur : Le mot de passe de l'enseignant doit contenir au minimum 1 caractère.");

			// On vérifie que l'enseignant n'existe pas déjà
			List<Enseignant> enseignants = enseignantRepository.findAll();
			for(Enseignant enseignant : enseignants){
				if(dataFetchingEnvironment.getArgument("mail").equals(enseignant.getMail())) return new Error("createEnseignant", "ALREADY_EXISTS", "Erreur : Un enseignant correspondant au mail : '" + enseignant.getMail() + "' existe déjà.");
			}

			// On crée le nouvel enseignant
			Enseignant nouvelEnseignant = new Enseignant(dataFetchingEnvironment.getArgument("nom"), dataFetchingEnvironment.getArgument("mail"), dataFetchingEnvironment.getArgument("motdepasse"));
			// Sauvegarde dans la base de données
			enseignantRepository.save(nouvelEnseignant);
			return nouvelEnseignant;
	 	};
	 }

	 public DataFetcher<Object> updateEnseignant(){
	 	return dataFetchingEnvironment -> {
	 		// On vérifie que l'enseignant existe
			Optional<Enseignant> enseignant = enseignantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")));
			if(!enseignant.isPresent()) return new Error("updateEnseignant", "NOT_FOUND", "Erreur : Aucun enseignant correspondant à l'ID : '" + Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")) +  "' n'a été trouvé.");

			// On modifie les attributs passés en paramètres si les nouvelles valeurs sont valides
			if(dataFetchingEnvironment.containsArgument("nom")) {
				if(StringUtils.normalizeSpace(dataFetchingEnvironment.getArgument("nom")).length() <= 0) return new Error("updateEnseignant", "INVALID_ARG", "Erreur : Le nom de l'enseignant que vous avez saisi : '" + dataFetchingEnvironment.getArgument("nom") +  "' n'est pas correct.");
				enseignant.get().setNom(dataFetchingEnvironment.getArgument("nom"));
			}
			if(dataFetchingEnvironment.containsArgument("mail")) {
				if(StringUtils.normalizeSpace(dataFetchingEnvironment.getArgument("mail")).length() <= 0) return new Error("updateEnseignant", "INVALID_ARG", "Erreur : L'adresse mail de l'enseignant que vous avez saisie : '" + dataFetchingEnvironment.getArgument("mail") +  "' n'est pas correcte.");
				enseignant.get().setMail(dataFetchingEnvironment.getArgument("mail"));
			}
			if(dataFetchingEnvironment.containsArgument("motdepasse")) {
				if(StringUtils.normalizeSpace(dataFetchingEnvironment.getArgument("motdepasse")).length() <= 0) return new Error("updateEnseignant", "INVALID_ARG", "Erreur : Le mot de passe de l'enseignant doit contenir au minimum 1 caractère.");
				enseignant.get().setMotdepasse(dataFetchingEnvironment.getArgument("motdepasse"));
			}
			if(dataFetchingEnvironment.containsArgument("repertoires")){
				// On supprime les répertoires qui ne font pas partie de la nouvelle liste de répertoire
				List<Map<String, Object>> repertoiresInput = dataFetchingEnvironment.getArgument("repertoires");
				List<Repertoire> enseignantRepertoires = enseignant.get().getRepertoires();
				List<Repertoire> repertoiresToRemove = new ArrayList<>();
				for(Repertoire repertoire : enseignantRepertoires){
					boolean remove = true;
					for(Map<String, Object> repertoireInput : repertoiresInput){
						Map<String, Object> enseignantInput = (Map<String, Object>) repertoireInput.get("enseignant");
						if(repertoire.getNom().equals(repertoireInput.get("nom")) && repertoire.getEnseignant().getMail().equals(enseignantInput.get("mail"))){
							remove = false;
							break;
						}
					}
					if(remove) repertoiresToRemove.add(repertoire);
					if(enseignant.get().getRepertoires().isEmpty()) break;
				}
				enseignant.get().removeRepertoire(repertoiresToRemove);
				// On ajoute les nouveaux répertoires
				List<Repertoire> repertoires = repertoireRepository.findAll();
				for(Map<String, Object> repertoireInput : repertoiresInput) {
					boolean repertoireExists = false;
					Map<String, Object> enseignantInput = (Map<String, Object>) repertoireInput.get("enseignant");
					for (Repertoire repertoire : repertoires) {
						if(repertoire.getNom().equals(repertoireInput.get("nom")) && repertoire.getEnseignant().getMail().equals(enseignantInput.get("mail"))){
							if(!enseignant.get().getRepertoires().contains(repertoire)){//------------------------------------------------
								if(repertoire.getEnseignant().getId_ens() != enseignant.get().getId_ens()) return new Error("updateEnseignant", "NOT_FOUND", "Erreur : Le répertoire '" + repertoireInput.get("nom") +  "' n'appartient pas à l'enseignant : '" + enseignant.get().getMail() + "'.");
								enseignant.get().addRepertoire(repertoire);
							}
							repertoireExists = true;
							break;
						}
					}
					if(!repertoireExists) return new Error("updateEnseignant", "NOT_FOUND", "Erreur : Le répertoire '" + repertoireInput.get("nom") + "' de l'enseignant : '" + enseignantInput.get("mail") + "' n'existe pas.");
				}
			}

			// Sauvegarde dans la base de données
			enseignantRepository.save(enseignant.get());
			return enseignant;
		};
	 }

	public DataFetcher<Object> removeEnseignant(){
		return dataFetchingEnvironment -> {
			// On vérifie que l'enseignant existe
			Optional<Enseignant> enseignant = enseignantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")));
			if(!enseignant.isPresent()) return new Error("removeEnseignant", "NOT_FOUND", "Erreur : Aucun enseignant correspondant à l'ID : '" + Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")) + "' n'a été trouvé.");

			// Suppression de l'enseignant de la base de données
			enseignantRepository.delete(enseignant.get());
			return enseignant;
		};
	}
}
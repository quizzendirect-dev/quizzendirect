package fr.univ.angers.quizz.api.datasfetchers;

import fr.univ.angers.quizz.api.model.Enseignant;
import fr.univ.angers.quizz.api.model.Etudiant;
import fr.univ.angers.quizz.api.model.Salon;
import fr.univ.angers.quizz.api.repository.EtudiantRepository;
import fr.univ.angers.quizz.api.repository.SalonRepository;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EtudiantDataFetcher {

    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired
    private SalonRepository salonRepository;

    public DataFetcher<List<Etudiant>> getAllProduct(){
        return dataFetchingEnvironment -> etudiantRepository.findAll();
    }

    public DataFetcher<Object> getEtudiantById(){
        return dataFetchingEnvironment -> {
            Optional<Etudiant> etudiant = etudiantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_etud")));
            if(etudiant.isPresent()) return etudiant.get();
            return new Error("Erreur : Cet étudiant n'existe pas.");
        };
    }

    public DataFetcher<Object> createEtudiant(){
        return dataFetchingEnvironment -> {
            List<Etudiant> etudiants = etudiantRepository.findAll();
            for(Etudiant etudiant : etudiants) {
                if (dataFetchingEnvironment.getArgument("pseudo").equals(etudiant.getPseudo()) && Integer.parseInt(dataFetchingEnvironment.getArgument("id_salon")) == etudiant.getSalon().getId_salon()) {
                    return new Error("Erreur : Cet étudiant existe déjà.");
                }
            }

            Optional<Salon> salon = salonRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_salon")));
            if(!salon.isPresent()) return new Error("Erreur : Ce salon n'existe pas.");

            Etudiant nouvelEtudiant = new Etudiant();
            nouvelEtudiant.setPseudo(dataFetchingEnvironment.getArgument("pseudo"));
            nouvelEtudiant.setSalon(salon.get());
            etudiantRepository.save(nouvelEtudiant);
            return nouvelEtudiant;
        };
    }

    public DataFetcher<Object> updatePseudo(){
        return dataFetchingEnvironment -> {
            Optional<Etudiant> etudiant = etudiantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_etud")));
            if(!etudiant.isPresent()) return new Error("Erreur : Cet étudiant n'existe pas.");

            etudiant.get().setPseudo(dataFetchingEnvironment.getArgument("pseudo"));
            etudiantRepository.save(etudiant.get());
            return etudiant;
        };
    }

    public DataFetcher<Object> updateSalon(){
        return dataFetchingEnvironment -> {
            Optional<Etudiant> etudiant = etudiantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_etud")));
            if(!etudiant.isPresent()) return new Error("Erreur : Cet étudiant n'existe pas.");

            Optional<Salon> salon = salonRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_salon")));
            if(!salon.isPresent()) return new Error("Erreur : Ce salon n'existe pas.");

            etudiant.get().setSalon(salon.get());
            etudiantRepository.save(etudiant.get());
            return etudiant;
        };
    }

    public DataFetcher<Object> incrementerBonnesReponses(){
        return dataFetchingEnvironment -> {
            Optional<Etudiant> etudiant = etudiantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_etud")));
            if(!etudiant.isPresent()) return new Error("Erreur : Cet étudiant n'existe pas.");

            etudiant.get().addBonneReponse();
            etudiantRepository.save(etudiant.get());
            return etudiant;
        };
    }

    public DataFetcher<Object> incrementerQuestionsRepondues(){
        return dataFetchingEnvironment -> {
            Optional<Etudiant> etudiant = etudiantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_etud")));
            if(!etudiant.isPresent()) return new Error("Erreur : Cet étudiant n'existe pas.");

            etudiant.get().addQuestionsRepondues();
            etudiantRepository.save(etudiant.get());
            return etudiant;
        };
    }

    public DataFetcher<Object> removeEtudiant(){
        return dataFetchingEnvironment -> {
            Optional<Etudiant> etudiant = etudiantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_etud")));
            if(!etudiant.isPresent()) return new Error("Erreur : Cet étudiant n'existe pas.");

            etudiantRepository.delete(etudiant.get());
            return etudiant;
        };
    }
}

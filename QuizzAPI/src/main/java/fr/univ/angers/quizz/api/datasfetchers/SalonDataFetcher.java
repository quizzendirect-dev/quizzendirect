package fr.univ.angers.quizz.api.datasfetchers;

import fr.univ.angers.quizz.api.model.Enseignant;
import fr.univ.angers.quizz.api.model.Etudiant;
import fr.univ.angers.quizz.api.model.Question;
import fr.univ.angers.quizz.api.model.Salon;
import fr.univ.angers.quizz.api.repository.EnseignantRepository;
import fr.univ.angers.quizz.api.repository.EtudiantRepository;
import fr.univ.angers.quizz.api.repository.QuestionRepository;
import fr.univ.angers.quizz.api.repository.SalonRepository;
import graphql.schema.DataFetcher;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@Component
public class SalonDataFetcher {

    @Autowired
    private SalonRepository salonRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;

    public DataFetcher<List<Salon>> getAllProduct(){
        return dataFetchingEnvironment -> salonRepository.findAll();
    }

    public DataFetcher<Object> getSalonById(){
        return dataFetchingEnvironment -> {
            Optional<Salon> salon = salonRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_salon")));
            if(salon.isPresent()) return salon.get();
            return new Error("Erreur : Ce salon n'existe pas.");
        };
    }

    public DataFetcher<Object> createSalon(){
        return dataFetchingEnvironment -> {
            List<Salon> salons = salonRepository.findAll();
            for(Salon salon : salons) {
                if (dataFetchingEnvironment.getArgument("codeAcces").equals(salon.getCodeAcces())) {
                    return new Error("Erreur : Ce salon existe déjà.");
                }
            }

            Optional<Enseignant> enseignant = enseignantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")));
            if(!enseignant.isPresent()) return new Error("Erreur : Cet enseignant n'existe pas.");

            Salon nouveauSalon = new Salon();
            nouveauSalon.setCodeAcces(dataFetchingEnvironment.getArgument("codeAcces"));
            nouveauSalon.setEnseignant(enseignant.get());
            salonRepository.save(nouveauSalon);
            return nouveauSalon;
        };
    }

    public DataFetcher<Object> updateCodeAcces(){
        return dataFetchingEnvironment -> {
            Optional<Salon> salon = salonRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_salon")));
            if(!salon.isPresent()) return new Error("Erreur : Ce salon n'existe pas.");

            salon.get().setCodeAcces(dataFetchingEnvironment.getArgument("codeAcces"));
            salonRepository.save(salon.get());
            return salon;
        };
    }

    public DataFetcher<Object> updateEnseignant(){
        return dataFetchingEnvironment -> {
            Optional<Salon> salon = salonRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_salon")));
            if(!salon.isPresent()) return new Error("Erreur : Ce salon n'existe pas.");

            Optional<Enseignant> enseignant = enseignantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_ens")));
            if(!enseignant.isPresent()) return new Error("Erreur : Cet enseignant n'existe pas.");

            salon.get().setEnseignant(enseignant.get());
            salonRepository.save(salon.get());
            return salon;
        };
    }

    public DataFetcher<Object> ajouterQuestion(){
        return dataFetchingEnvironment -> {
            Optional<Salon> salon = salonRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_salon")));
            if(!salon.isPresent()) return new Error("Erreur : Ce salon n'existe pas.");

            Optional<Question> question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")));
            if(!question.isPresent()) return new Error("Erreur : Cette question n'existe pas.");

            salon.get().addQuestion(question.get());
            salonRepository.save(salon.get());
            return salon;
        };
    }

    public DataFetcher<Object> supprimerQuestion(){
        return dataFetchingEnvironment -> {
            Optional<Salon> salon = salonRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_salon")));
            if(!salon.isPresent()) return new Error("Erreur : Ce salon n'existe pas.");

            Optional<Question> question = questionRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_quest")));
            if(!question.isPresent()) return new Error("Erreur : Cette question n'existe pas.");

            salon.get().removeQuestion(question.get());
            salonRepository.save(salon.get());
            return salon;
        };
    }

    public DataFetcher<Object> ajouterEtudiant(){
        return dataFetchingEnvironment -> {
            Optional<Salon> salon = salonRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_salon")));
            if(!salon.isPresent()) return new Error("Erreur : Ce salon n'existe pas.");

            Optional<Etudiant> etudiant = etudiantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_etud")));
            if(!etudiant.isPresent()) return new Error("Erreur : Cet étudiant n'existe pas.");
            salon.get().addEtudiant(etudiant.get());
            salonRepository.save(salon.get());
            return salon;
        };
    }

    public DataFetcher<Object> supprimerEtudiant(){
        return dataFetchingEnvironment -> {
            Optional<Salon> salon = salonRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_salon")));
            if(!salon.isPresent()) return new Error("Erreur : Ce salon n'existe pas.");

            Optional<Etudiant> etudiant = etudiantRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_etud")));
            if(!etudiant.isPresent()) return new Error("Erreur : Cet étudiant n'existe pas.");

            salon.get().removeEtudiant(etudiant.get());
            salonRepository.save(salon.get());
            return salon;
        };
    }

    public DataFetcher<Object> removeSalon(){
        return dataFetchingEnvironment -> {
            Optional<Salon> salon = salonRepository.findById(Integer.parseInt(dataFetchingEnvironment.getArgument("id_salon")));
            if(!salon.isPresent()) return new Error("Erreur : Ce salon n'existe pas.");

            salonRepository.delete(salon.get());
            return salon;
        };
    }
}

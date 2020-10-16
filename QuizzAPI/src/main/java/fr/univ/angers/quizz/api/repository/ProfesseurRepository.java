package fr.univ.angers.quizz.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fr.univ.angers.quizz.api.model.Professeur;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesseurRepository extends CrudRepository<Professeur, Integer> {

    /**
     * @param code
     * @return
     */
    public Professeur findByNom(String code);

    /**
     * @return
     */
    @Override
    public List<Professeur> findAll();

}


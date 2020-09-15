package fr.univ.angers.quizz.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fr.univ.angers.quizz.api.model.Professeur;

public interface ProfesseurRepository  extends CrudRepository<Professeur, Long> {

    /**
     * @param code
     * @return
     */
    public Professeur findByName(String code);

    /**
     * @return
     */
    @Override
    public List<Professeur> findAll();
    
}


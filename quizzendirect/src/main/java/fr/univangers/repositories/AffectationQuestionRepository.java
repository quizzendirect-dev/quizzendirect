package fr.univangers.repositories;

import fr.univangers.models.AffectationQuestion;
import fr.univangers.models.AffectationQuestionId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffectationQuestionRepository extends CrudRepository<AffectationQuestion, AffectationQuestionId> {
}

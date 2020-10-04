package fr.univangers.repositories;

import fr.univangers.models.AffectationEnseignant;
import fr.univangers.models.AffectationEnseignantId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffectationEnseignantRepository extends CrudRepository<AffectationEnseignant, AffectationEnseignantId> {
}

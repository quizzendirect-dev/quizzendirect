

package fr.univ.angers.quizz.api.resolvers;


import fr.univ.angers.quizz.api.model.Enseignant;
import fr.univ.angers.quizz.api.model.Salon;
import fr.univ.angers.quizz.api.publisher.SalonPublisher;
import fr.univ.angers.quizz.api.repository.SalonRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SalonMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private SalonRepository salonRepository;

    @Autowired
    private SalonPublisher salonPublisher;

    public Salon createSalon(int codeAcces, Enseignant enseignant) {
        Salon salon = new Salon(codeAcces,enseignant);

        // DBへ登録
        Salon newsalon = this.salonRepository.save(salon);
        // データ変更を通知
        salonPublisher.publish(newsalon);

        return newsalon;
    }
}
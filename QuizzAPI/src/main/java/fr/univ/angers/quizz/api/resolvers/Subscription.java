

package fr.univ.angers.quizz.api.resolvers;

import fr.univ.angers.quizz.api.model.Salon;
import fr.univ.angers.quizz.api.publisher.SalonPublisher;
import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Subscription implements GraphQLSubscriptionResolver {

    @Autowired
    private SalonPublisher salonPublisher;

    public Publisher<Salon> pushAuthors() {
        // クライアントへ通知
        return salonPublisher.getPublisher();
    }
}

package fr.univ.angers.quizz.api;

import fr.univ.angers.quizz.api.model.Salon;
import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Component
public class SubscriptionResolver implements GraphQLSubscriptionResolver {

    public Publisher<Salon> salon(int codeAccess) {
       /* Random random = new Random();
        return Flux.interval(Duration.ofSeconds(1))
                .map(num -> new Salon(codeAccess, null ));
*/
        return Flux.interval(Duration.ofSeconds(1))
                .map( num -> new Salon(codeAccess,null));
    }

}
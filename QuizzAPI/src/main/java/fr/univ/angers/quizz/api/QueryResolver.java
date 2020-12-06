
package fr.univ.angers.quizz.api;

import fr.univ.angers.quizz.api.model.Salon;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class QueryResolver implements GraphQLQueryResolver {

    public Salon salon(int codeAccess) {
        return new Salon(codeAccess,null);
    }


}
package fr.univ.angers.quizz.api.resolver;

import com.coxautodev.graphql.tools.*;
import fr.univ.angers.quizz.api.model.User;
import fr.univ.angers.quizz.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserQuery implements GraphQLQueryResolver {

    @Autowired
    private UserService userService;


    public User getUser(String username, String password) {
        return userService.getUser(username, password);
    }
}

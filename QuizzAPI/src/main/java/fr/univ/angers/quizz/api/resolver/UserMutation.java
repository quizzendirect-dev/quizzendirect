package fr.univ.angers.quizz.api.resolver;

import com.coxautodev.graphql.tools.*;
import fr.univ.angers.quizz.api.model.User;
import fr.univ.angers.quizz.api.security.AdminSecured;
import fr.univ.angers.quizz.api.security.Unsecured;
import fr.univ.angers.quizz.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMutation implements GraphQLMutationResolver {

    @Autowired
    UserService userService;

    @Unsecured
    public User createUser(String username, String password) {
        return userService.createUser(username, password);
    }

    @AdminSecured
    public User deleteUser(String username) {
        return userService.deleteUser(username);
    }
}

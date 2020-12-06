package fr.univ.angers.quizz.api.service;

import fr.univ.angers.quizz.api.error.UserAlreadyExistsException;
import fr.univ.angers.quizz.api.error.UserNotFoundException;
import fr.univ.angers.quizz.api.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    List<User> users = new ArrayList<>();

    public User createUser(String username, String password) {
        if (userExists(username))
            throw new UserAlreadyExistsException("A user already exists with this username, please try another one");
        User user = new User();
        user.setId(users.size() + 1);
        user.setPassword(password);
        user.setUsername(username);
        users.add(user);
        return user;
    }

    public User getUser(String username, String password) {
        for (User user : users)
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        throw new UserNotFoundException("We were unable to find a user with the provided credentials", "username");
    }

    private boolean userExists(String username) {
        for (User user : users)
            if (user.getUsername().equals(username))
                return true;
        return false;
    }

    public User deleteUser(String username) {
        for (User user : users)
            if (user.getUsername().equals(username)) {
                users.remove(user);
                return user;
            }
        return null;
    }
}

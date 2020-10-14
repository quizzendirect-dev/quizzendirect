package fr.univangers;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CompteController {

    @GetMapping("/connection")
    public String connexion(Model model,
                            // récupération des cookies
                            @CookieValue(value="userName",defaultValue = "") String userName,
                            @CookieValue(value="userEmail",defaultValue = "") String userEmail) {
        // test si le cookie existe ou est vide
        if(!userEmail.equals("")) {
            return "hubGestion";
        }
        else {
            model.addAttribute("user", new User());
            return "comptePage";
        }
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model,HttpServletResponse response) {

        // debug console
        System.out.println("\n\n\n###### Test register ######");
        System.out.println("Name : " + user.getName());
        System.out.println("Email : " + user.getEmail());
        System.out.println("Password : " + user.getPasswd());

        // création cookie
        // ***il faudrait ajouter l'utilisateur dans la bdd***
        Cookie userName = new Cookie("userName",user.getName());
        userName.setMaxAge(30*24*60*60); // expire dans 30 jours
        userName.setPath("/"); // global cookie ( accessible partout )
        Cookie userEmail = new Cookie("userEmail", user.getEmail());
        userEmail.setMaxAge(30*24*60*60); // expire dans 30 jours
        userEmail.setPath("/"); // global cookie ( accessible partout )
        // ajout des cookies dans la réponse
        response.addCookie(userName);
        response.addCookie(userEmail);

        // verifier dans le navigateur que le cookie a bien été créé ( inspecter->stockage )

        model.addAttribute("userName", user.getName());
        return "hubGestion";
    }

    @PostMapping("/login")
    public String logIn(@ModelAttribute User user, Model model, HttpServletResponse response) {

        // debug console
        System.out.println("\n\n\n###### Test login ######");
        System.out.println("Name : " + user.getName());
        System.out.println("Email : " + user.getEmail());
        System.out.println("Password : " + user.getPasswd());
        // création cookie
        // ***il faudrait vérifié que le mot de passe correspond à l'email saisie
        // ensuite récupérer le nom de l'utilisateur qui correspond***
        Cookie userName = new Cookie("userName",user.getName());
        userName.setMaxAge(30*24*60*60); // expire dans 30 jours
        userName.setPath("/"); // global cookie ( accessible partout )
        Cookie userEmail = new Cookie("userEmail", user.getEmail());
        userEmail.setMaxAge(30*24*60*60); // expire dans 30 jours
        userEmail.setPath("/"); // global cookie ( accessible partout )

        // ajout des cookies dans la réponse
        response.addCookie(userName);
        response.addCookie(userEmail);

        // verifier dans le navigateur que le cookie a bien été créé ( inspecter->stockage )

        model.addAttribute("userName", user.getName());
        return "hubGestion";
    }

}

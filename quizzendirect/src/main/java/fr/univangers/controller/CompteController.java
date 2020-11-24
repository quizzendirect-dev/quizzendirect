package fr.univangers.controller;

import fr.univangers.User;
import fr.univangers.models.Enseignant;
import fr.univangers.repositories.EnseignantRepository;
import fr.univangers.services.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;


@Controller
public class CompteController {

    @Autowired
    private EnseignantService enseignantService;

    @GetMapping("/connection")
    public String connexion(Model model,
                            // récupération des cookies
                            @CookieValue(value="userId_ens",defaultValue = "") String userId_ens,
                            @CookieValue(value="userPrenom",defaultValue = "") String userPrenom,
                            @CookieValue(value="userName",defaultValue = "") String userName,
                            @CookieValue(value="userEmail",defaultValue = "") String userEmail) {
        // test si le cookie existe ou est vide
        if(!userEmail.equals("")) {
            User user = new User();
            if(!userId_ens.equals(""))
                user.setId(Integer.parseInt(userId_ens));
            user.setEmail(userEmail);
            user.setPrenom(userPrenom); user.setName(userName);
            // debug console
            System.out.println("\n\n\n###### Test register ######");
            System.out.println("Ens' id : " + user.getId());
            System.out.println("Ens' Prenom : " + user.getPrenom());
            System.out.println("Ens' Nom : " + user.getName());
            System.out.println("Ens' mail : " + user.getEmail());

            model.addAttribute("user", user);
            return "hubGestion";
        }
        else {
            model.addAttribute("user", new User());
            return "comptePage";
        }
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model,HttpServletResponse response) {

        // vérifier que le mail n'existe pas déja
        ArrayList<Enseignant> enseignants = enseignantService.getAllEnseignants();

        for (Enseignant enseignant : enseignants) {
            if(enseignant.getMail().equals(user.getEmail())) {
                model.addAttribute("email_exists",true);
                return "/comptePage";
            }
        }
        // arrivé là, l'email n'est pas présent dans la bdd, on peut créé un nouvel enseignant
        // création nouvel enseignant
        Enseignant enseignant = new Enseignant(user.getName(),user.getEmail(),user.getPasswd());
        enseignantService.addEnseignant(enseignant);

        // debug console
        System.out.println("\n\n\n###### Test register ######");
        System.out.println("Ens' id : " + enseignant.getId_ens());
        System.out.println("Ens' Nom : " + enseignant.getNom());
        System.out.println("Ens' mail : " + enseignant.getMail());
        System.out.println("Ens' pass : " + enseignant.getMotdepasse());

        // création cookie
        // ***il faudrait ajouter l'utilisateur dans la bdd***
        // userId
        Cookie userId_ens = new Cookie("userId_ens", String.valueOf(enseignant.getId_ens()));
        userId_ens.setMaxAge(30 * 24 * 60 * 60); // expire dans 30 jours
        userId_ens.setPath("/"); // global cookie ( accessible partout )
        // userprenom
        Cookie userPrenom = new Cookie("userPrenom",user.getPrenom().trim().replaceAll(" ","_"));
        userPrenom.setMaxAge(30*24*60*60); // expire dans 30 jours
        userPrenom.setPath("/"); // global cookie ( accessible partout )
        // username
        Cookie userName = new Cookie("userName",user.getName().trim().replaceAll(" ","_"));
        userName.setMaxAge(30*24*60*60); // expire dans 30 jours
        userName.setPath("/"); // global cookie ( accessible partout )
        // useremail
        Cookie userEmail = new Cookie("userEmail", user.getEmail());
        userEmail.setMaxAge(30*24*60*60); // expire dans 30 jours
        userEmail.setPath("/"); // global cookie ( accessible partout )

        // ajout des cookies dans la réponse
        response.addCookie(userId_ens);
        response.addCookie(userPrenom);
        response.addCookie(userName);
        response.addCookie(userEmail);


        // verifier dans le navigateur que le cookie a bien été créé ( inspecter->stockage )

        model.addAttribute("userName", user.getName());
        return "hubGestion";
    }

    @PostMapping("/login")
    public String logIn(@ModelAttribute User user, Model model, HttpServletResponse response) {

        // debug console
        /*System.out.println("\n\n\n###### Test login ######");
        System.out.println("Prenom : " + user.getPrenom());
        System.out.println("Name : " + user.getName());
        System.out.println("Email : " + user.getEmail());
        System.out.println("Password : " + user.getPasswd());*/

        ArrayList<Enseignant> enseignants = enseignantService.getAllEnseignants();

        for (Enseignant enseignant : enseignants) {
            /*System.out.println("enseignant : \n");
            System.out.println(enseignant.getMail()+"\n");
            System.out.println(enseignant.getMotdepasse()+"\n");*/
            // si l'enseignant correspond à un enseignant déja enregistré
            if (enseignant.getMail().equals(user.getEmail()) && enseignant.getMotdepasse().equals(user.getPasswd())) {
                // compléter l'object user envoyé depuis la page login avec le prénom et le nom et l'id
                user.setName(enseignant.getNom());
                user.setId(enseignant.getId_ens());
                // création cookie
                // userId
                Cookie userId_ens = new Cookie("userId_ens", String.valueOf(user.getId()));
                userId_ens.setMaxAge(30 * 24 * 60 * 60); // expire dans 30 jours
                userId_ens.setPath("/"); // global cookie ( accessible partout )
                // userprenom
                Cookie userPrenom = new Cookie("userPrenom", user.getPrenom().trim().replaceAll(" ", "_"));
                userPrenom.setMaxAge(30 * 24 * 60 * 60); // expire dans 30 jours
                userPrenom.setPath("/"); // global cookie ( accessible partout )
                // usernom
                Cookie userName = new Cookie("userName", user.getName().trim().replaceAll(" ", "_"));
                userName.setMaxAge(30 * 24 * 60 * 60); // expire dans 30 jours
                userName.setPath("/"); // global cookie ( accessible partout )
                // useremail
                Cookie userEmail = new Cookie("userEmail", user.getEmail());
                userEmail.setMaxAge(30 * 24 * 60 * 60); // expire dans 30 jours
                userEmail.setPath("/"); // global cookie ( accessible partout )

                // ajout des cookies dans la réponse
                response.addCookie(userId_ens);
                response.addCookie(userPrenom);
                response.addCookie(userName);
                response.addCookie(userEmail);

                // verifier dans le navigateur que le cookie a bien été créé ( inspecter->stockage )

                model.addAttribute("userName", user.getName());
                return "hubGestion";
            }
        }
        // il faudrait signaler qu'une erreur a eu lieu
        model.addAttribute("errorLogin","L'email ou le mot de passe ne sont pas valides.");
        return "comptePage";
    }
}

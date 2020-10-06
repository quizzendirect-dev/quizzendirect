package fr.univangers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CompteController {

    @GetMapping("/connection")
    public String connexion(Model model) {
        model.addAttribute("user", new User());
        return "comptePage";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {

        System.out.println("\n\n\n###### Test register ######");
        System.out.println("Name : " + user.getName());
        System.out.println("Email : " + user.getEmail());
        System.out.println("Password : " + user.getPasswd());

        model.addAttribute("userName", user.getName());
        return "hubGestion";
    }

    @PostMapping("/login")
    public String logIn(@ModelAttribute User user, Model model) {

        System.out.println("\n\n\n###### Test login ######");
        System.out.println("Name : " + user.getName());
        System.out.println("Email : " + user.getEmail());
        System.out.println("Password : " + user.getPasswd());

        model.addAttribute("userName", user.getName());
        return "hubGestion";
    }

}

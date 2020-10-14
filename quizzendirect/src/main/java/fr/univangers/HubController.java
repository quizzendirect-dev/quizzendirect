package fr.univangers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HubController {

    @GetMapping("/hub")
    public String hub() {
        return "hubGestion";
    }

    @PostMapping("/disconnection")
    public String disconnection(Model model, HttpServletResponse response) {
        // destroy user cookies
        // userId
        Cookie userId_ens = new Cookie("userId_ens", null);
        userId_ens.setMaxAge(0); // expire de suite
        userId_ens.setPath("/"); // global cookie ( accessible partout )
        // userprenom
        Cookie userPrenom = new Cookie("userPrenom", null);
        userPrenom.setMaxAge(0); // expire de suite
        userPrenom.setPath("/"); // global cookie ( accessible partout )
        // usernom
        Cookie userName = new Cookie("userName", null);
        userName.setMaxAge(0); // expire de suite
        userName.setPath("/"); // global cookie ( accessible partout )
        // useremail
        Cookie userEmail = new Cookie("userEmail", null);
        userEmail.setMaxAge(0); // expire de suite
        userEmail.setPath("/"); // global cookie ( accessible partout )

        // ajout des cookies dans la réponse
        response.addCookie(userId_ens);
        response.addCookie(userPrenom);
        response.addCookie(userName);
        response.addCookie(userEmail);

        model.addAttribute("user", new User());
        return "comptePage";
    }

}

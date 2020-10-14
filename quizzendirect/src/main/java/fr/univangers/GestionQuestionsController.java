

package fr.univangers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GestionQuestionsController {

    @GetMapping("/GestionQuestions")
    public String getGestionQuestions() {
        return "GestionQuestions";
    }

}

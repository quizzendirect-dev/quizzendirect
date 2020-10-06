package fr.univangers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HubController {

    @GetMapping("/hub")
    public String hub() {
        return "hubGestion";
    }

    @PostMapping("/disconnection")
    public String disconnection(Model model) {
        model.addAttribute("user", new User());
        return "comptePage";
    }

}

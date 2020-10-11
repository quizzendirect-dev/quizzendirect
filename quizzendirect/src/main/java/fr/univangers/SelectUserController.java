
package fr.univangers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SelectUserController {
    @GetMapping("/SelectPage")
    public String getSelectedPage(){
            return "SelectionPage";
    }
}
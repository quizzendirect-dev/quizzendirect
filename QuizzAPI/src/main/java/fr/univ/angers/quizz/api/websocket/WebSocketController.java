package fr.univ.angers.quizz.api.websocket;

import fr.univ.angers.quizz.api.model.Etudiant;
import fr.univ.angers.quizz.api.model.Salon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private Logger logger = LogManager.getLogger(WebSocketController.class);

    @MessageMapping("/salon.ajouterEtudiant")
    @SendTo("/salon")
    public Salon addUser(@Payload Etudiant etudiant, SimpMessageHeaderAccessor headerAccessor) {
        logger.info("WebSocketController : addUser()");
        // TODO recupérer le salon à partir de l'idSalon de l'étudiant
        // salon.nbEtudiant++;
        return new Salon();
    }

    @MessageMapping("/salon.nouveau")
    @SendTo("/salon")
    public Salon createSalon(Salon salon) {
        logger.info("WebSocketController : createSalon()");
        return new Salon(); //TODO creation du salon
    }

    @MessageMapping("/salon.update")
    @SendTo("/salon")
    public Salon updateSalon(@Payload Salon salon) {
        logger.info("WebSocketController : updateSalon()");
        return salon;
    }

}

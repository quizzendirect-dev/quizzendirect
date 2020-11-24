package fr.univ.angers.quizz.api.websocket;

import fr.univ.angers.quizz.api.model.Question;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private Logger logger = LogManager.getLogger(WebSocketController.class);

    @MessageMapping("/salon")
    @SendTo("/quiz/salon")
    public Question getCurrentQuestion(Question question) {
        logger.info("WebSocketController : createSalon()");
        return question;
    }

}

package fr.univ.angers.quizz.api.websocket;

import fr.univ.angers.quizz.api.model.Etudiant;
import fr.univ.angers.quizz.api.model.Salon;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/salon.addUser")
    @SendTo("/topic/publicChatRoom")
    public Salon addUser(@Payload Salon salon, @Payload Etudiant etudiant, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", etudiant.getPseudo());
        return salon;
    }

}

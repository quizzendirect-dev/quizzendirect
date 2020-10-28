package fr.univangers.websocket;

import fr.univangers.models.Etudiant;
import fr.univangers.models.Salon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private Logger logger = LogManager.getLogger(MyStompSessionHandler.class);
    private StompSession session;

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        this.session = session;
        this.session.subscribe("/quiz/salon", this);
        logger.info("StompSessionHandler : afterConnected()");
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Salon salon = (Salon) payload;
        logger.info("StompSessionHandler : handleFrame()");
    }

    public void addEtudiant(Etudiant etudiant) {
        logger.info("StompSessionHandler : addEtudiant()");
        this.session.send("/app/salon.ajouterEtudiant", etudiant);
    }

    public void newSalon(Salon salon) {
        logger.info("StompSessionHandler : newSalon()");
        logger.info("StompSessionHandler : session connected ? " + this.session.isConnected());
        this.session.send("/app/salon.nouveau", salon);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

}

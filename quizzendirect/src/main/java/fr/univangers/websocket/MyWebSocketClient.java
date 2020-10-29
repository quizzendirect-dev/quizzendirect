package fr.univangers.websocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class MyWebSocketClient {

    // url pour la connexion du websocket
    private static final String URL = "ws://localhost:20020/ws";
    private Logger logger = LogManager.getLogger(MyWebSocketClient.class);

    @Autowired
    private WebSocketStompClient stompClient;
    private MyStompSessionHandler sessionHandler;

    public MyStompSessionHandler getSessionHandler() {
        return sessionHandler;
    }

    public void openConnection() {
        logger.info("WebSocketClient : openConnection()");
        WebSocketClient client = new StandardWebSocketClient();
        logger.info("WebSocketClient : openConnection()2");
        stompClient = new org.springframework.web.socket.messaging.WebSocketStompClient(client);
        logger.info("WebSocketClient : openConnection()3");
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        logger.info("WebSocketClient : openConnection()4");
        sessionHandler = new MyStompSessionHandler();
        logger.info("WebSocketClient : openConnection()5");
        stompClient.connect(URL, sessionHandler);
        stompClient.start();
        logger.info("WebSocketClient : isRunning ? " + stompClient.isRunning());
    }

}

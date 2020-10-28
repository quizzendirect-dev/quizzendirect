package fr.univangers.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import static org.hibernate.cfg.AvailableSettings.URL;

public class MyWebSocketClient {

    @Autowired
    private WebSocketStompClient stompClient;

    public void openConnection() {
        WebSocketClient client = new StandardWebSocketClient();

        stompClient = new org.springframework.web.socket.messaging.WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        stompClient.connect(URL, sessionHandler);
    }

}

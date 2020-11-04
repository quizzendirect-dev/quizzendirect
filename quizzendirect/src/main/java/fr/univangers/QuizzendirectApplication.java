package fr.univangers;

import fr.univangers.models.Salon;
import fr.univangers.websocket.MyWebSocketClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizzendirectApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizzendirectApplication.class, args);

		//TEST DU WEBSOCKET
//		MyWebSocketClient client = new MyWebSocketClient();
//		client.openConnection();
//		Salon salon = new Salon();
//		client.getSessionHandler().newSalon(salon);
	}

}

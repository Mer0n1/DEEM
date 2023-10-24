package com.example.push_notification_service;

import com.example.push_notification_service.EndPoints.MainContainer;
import com.example.push_notification_service.EndPoints.MessengerEndPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;


@SpringBootApplication
public class PushNotificationServiceApplication {

	public static void main(String[] args) {
		org.glassfish.tyrus.server.Server server = new org.glassfish.tyrus.server.Server
				("0.0.0.0", 8025, "/ws", MessengerEndPoint.class);

		try {
			server.start();
			System.out.println("Press any key to stop the server..");
			new Scanner(System.in).nextLine();
		} catch (javax.websocket.DeploymentException e) {
			throw new RuntimeException(e);
		} finally {
			server.stop();
		}

		SpringApplication.run(PushNotificationServiceApplication.class, args);
	}

}

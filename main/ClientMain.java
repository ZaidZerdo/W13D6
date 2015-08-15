package main;

import gui.PlayField;
import connection.Client;

public class ClientMain {

	public static void main(String[] args) {
		System.out.println("[CLIENT]");
		PlayField window = new PlayField();
		Client.connectTo("127.0.0.1", window);
		
	}

}

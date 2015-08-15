package main;

import connection.Server;

public class ServerMain {

	public static void main(String[] args) {
		System.out.println("[SERVER]");
		Server.startServer();
		Server.startLookingForClients();
	}

}

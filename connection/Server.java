package connection;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import object.Board;

public class Server {
	
	public static final int PORT = 8888;

	private static ServerSocket server;
	private static ArrayList<Player> playerList;
	
	public static void startServer() {
		try {
			server = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		playerList = new ArrayList<>();
		System.out.println("Server started.");
	}
	
	public static void startLookingForClients() {
			try {
				while (true) {
					Socket client = server.accept();
					System.out.println("Client found!");
					
					ClientThread ct = new ClientThread(client);
					ct.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}			
	}
	
	static class ClientThread extends Thread {
		private Socket client;
		
		public ClientThread(Socket client) {
			this.client = client;
		}
		
		@Override
		public void run() {
			Point point = Board.getRandomCoordinates();
			Color color = Board.getRandomColor();
			
			Player player = new Player(client, point.x, point.y, color);
			playerList.add(player);
			ListUpdater.updateListOfPlayers();
			System.out.println("Player list updated with new player.");
			System.out.println("List: " + playerList);
			
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
				while (true) {
					String msg = in.readLine();
					System.out.println("Msg received: " + msg);
					
					int diffX = Integer.parseInt(msg.split(" ")[0]);
					int diffY = Integer.parseInt(msg.split(" ")[1]);
					
					for (Player p: playerList) {
						if (p.getSocket() == client) {
							p.changeCoordinates(diffX, diffY);
						}
					}
					System.out.println(playerList);
					
					ListUpdater.updateListOfPlayers();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	}
	
	static class ListUpdater extends Thread {
		
		public static void updateListOfPlayers() {
			ListUpdater updater = new ListUpdater();
			updater.start();
		}
		
		@Override
		public void run() {
			// 0 0 255 0 0; 5 5 0 0 255;
			String msg = "";
			for (Player p : playerList) {
				msg += p.getPlayerString() + ";";
			}
			
			for (Player p : playerList) {
				p.sendMessage(msg);
			}
		}
	}
}

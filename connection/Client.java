package connection;

import gui.PlayField;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

	private static Socket server;
	private static ArrayList<Player> playerList;
	private static PlayField playField;
	private static BufferedWriter writer;
	
	public static void connectTo(String IP, PlayField window) {
		playField = window;
		
		try {
			server = new Socket(IP, Server.PORT);
			writer = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
			System.out.println("Connected to " + IP + ".");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		playerList = new ArrayList<>();
		(new ListUpdater(server)).start();
	}
	
	public static void sendCoordinateChange(int diffX, int diffY) {
		try {
			writer.write(diffX + " " + diffY);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static class ListUpdater extends Thread {
		
		private Socket server;
		
		public ListUpdater(Socket server) {
			this.server = server;
		}
		
		@Override
		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			
				while (true) {
					System.out.println("Waiting for msg.");
					String msg = in.readLine();
					
					playerList.clear();
					String[] playerString = msg.split(";");
					
					// 0 0 255 0 0; 5 5 0 0 255;
					for (String s : playerString) {
						String[] info = s.split(" ");
						
						int x = Integer.parseInt(info[0]);
						int y = Integer.parseInt(info[1]);
						int r = Integer.parseInt(info[2]);
						int g = Integer.parseInt(info[3]);
						int b = Integer.parseInt(info[4]);
						Color c = new Color(r, g, b);
						
						playerList.add(new Player(null, x, y, c));
					}
					
					System.out.println("List: " + playerList);
					playField.updateFields(playerList);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	}
}

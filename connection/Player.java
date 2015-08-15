package connection;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Player {
	private Socket client;
	private BufferedWriter writer;
	private int x;
	private int y;
	private Color color;
	
	public Player(Socket client, int x, int y, Color color) {
		super();
		this.client = client;
		this.x = x;
		this.y = y;
		this.color = color;
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			writer = null;
		}
	}
	
	public void sendMessage(String msg) {
		try {
			writer.write(msg);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return client;
	}
	
	public void changeCoordinates(int diffX, int diffY) {
		x += diffX;
		y += diffY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Color getColor() {
		return color;
	}
	
	public String getPlayerString() {
		return x + " " + y + " " + color.getRed() + " " +
				color.getGreen() + " " + color.getBlue();
	}
	
	@Override
	public String toString() {
		return getPlayerString();
	}
	
}

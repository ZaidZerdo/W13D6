package object;

import java.awt.Color;
import java.awt.Point;

public class Board {
	
	public static final int BOARD_WIDTH = 20;
	public static final int BOARD_HEIGHT = 20;
	
	public static Point getRandomCoordinates() {
		int x = (int) (Math.random() * BOARD_WIDTH);
		int y = (int) (Math.random() * BOARD_HEIGHT);
		
		return new Point(x, y);
	}
	
	public static Color getRandomColor() {
		float r = (float) Math.random();
		float g = (float) Math.random();
		float b = (float) Math.random();
		
		return new Color(r, g, b);
	}
}

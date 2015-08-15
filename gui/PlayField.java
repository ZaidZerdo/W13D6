package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import object.Board;
import connection.Client;
import connection.Player;

public class PlayField extends JFrame {
	private static final long serialVersionUID = -8689592095378284416L;
	
	private JLabel[][] field = new JLabel[Board.BOARD_WIDTH][Board.BOARD_HEIGHT];
	
	public PlayField() {
		setLayout(new GridLayout(Board.BOARD_WIDTH, Board.BOARD_HEIGHT, 5, 5));
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				field[i][j] = new JLabel();
				field[i][j].setOpaque(true);
				field[i][j].setBackground(Color.LIGHT_GRAY);
				add(field[i][j]);
			}
		}
		
		addKeyListener(new Key());
		
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Mala igra");
		setVisible(true);
	}
	
	public void updateFields(ArrayList<Player> playerList) {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				field[i][j].setBackground(Color.LIGHT_GRAY);
			}
		}
		
		for (Player p : playerList) {
			field[p.getY()][p.getX()].setBackground(p.getColor());
		}
	}
	
	class Key extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			int diffX = 0;
			int diffY = 0;
			
			if (e.getKeyChar() == 'a') {
				diffX = -1;
			} else if (e.getKeyChar() == 'd') {
				diffX = 1;
			} else if (e.getKeyChar() == 'w') {
				diffY = -1;
			} else if (e.getKeyChar() == 's') {
				diffY = 1;
			}
			
			Client.sendCoordinateChange(diffX, diffY);
		}
	}
}

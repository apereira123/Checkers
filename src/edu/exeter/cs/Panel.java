package edu.exeter.cs;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Panel extends JPanel {

	public static boolean playing;
	public static int player;
	public static int selectedRow;
	public static int selectedCol;

	public Panel() {
		setLayout(new GridBagLayout());

		BoardGraphics board1 = new BoardGraphics();
		UserInterface board2 = new UserInterface();

		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridwidth = 8;
		c1.gridheight = 8;
		c1.gridx = 0;
		c1.gridy = 0;

		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridwidth = 8;
		c2.gridheight = 1;
		c2.gridx = 0;
		c2.gridy = 8;

		add(board1, c1);
		add(board2, c2);
	}

	public static boolean isPlaying() {
		return playing;
	}
	public static void setPlaying(boolean b) {
		playing = b;
	}
	
	public static int getPlayer() {
		return player;
	}
	public static void setPlayer(int n) {
		player = n;
	}

	public static int getSelectedRow() {
		return selectedRow;
	}
	public static void setSelectedRow(int n) {
		selectedRow = n;
	}

	public static int getSelectedCol() {
		return selectedCol;
	}
	public static void setSelectedCol(int n) {
		selectedCol  = n;
	}

}

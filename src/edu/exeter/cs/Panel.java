package edu.exeter.cs;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Panel extends JPanel {

	//Create static variables which can be accessed by 
	//both the BoardGraphics and UserInterface classes.
	public static boolean playing;
	public static int player;
	public static int selectedRow;
	public static int selectedCol;

	//Construct Panel.
	public Panel() {
		//Set the layout to the GridBagLayout manager.
		setLayout(new GridBagLayout());

		//Instantiate BoardGraphics and UserInterface.
		BoardGraphics board1 = new BoardGraphics();
		UserInterface board2 = new UserInterface();

		//Set constraints for board1.
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridwidth = 8;
		c1.gridheight = 8;
		c1.gridx = 0;
		c1.gridy = 0;

		//Set constraints for board2.
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridwidth = 8;
		c2.gridheight = 1;
		c2.gridx = 0;
		c2.gridy = 8;

		//Add both panels to the Panel class with their respective constraints.
		add(board1, c1);
		add(board2, c2);
	}

	//Methods to get and set playing.
	public static boolean isPlaying() {
		return playing;
	}
	public static void setPlaying(boolean b) {
		playing = b;
	}
	
	//Methods to get and set player.
	public static int getPlayer() {
		return player;
	}
	public static void setPlayer(int n) {
		player = n;
	}

	//Methods to get and set selectedRow.
	public static int getSelectedRow() {
		return selectedRow;
	}
	public static void setSelectedRow(int n) {
		selectedRow = n;
	}

	//Methods to get and set selectedCol.
	public static int getSelectedCol() {
		return selectedCol;
	}
	public static void setSelectedCol(int n) {
		selectedCol  = n;
	}

}
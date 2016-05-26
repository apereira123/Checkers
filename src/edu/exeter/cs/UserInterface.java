package edu.exeter.cs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class UserInterface extends JPanel implements ActionListener {

	// Declare components which will be added to the UserInterface panel.
	private static JButton newGame;
	private static JLabel label;
	private static JButton endGame;

	// Construct UserInterface.
	public UserInterface() {
		// Set up the layout manager, dimensions, and color of the panel.
		setLayout(new GridBagLayout());
		setBackground(Color.gray);
		setPreferredSize(new Dimension(700, 25));

		// newGame allows users to start a new game. It is enabled by default.
		newGame = new JButton("New Game");
		newGame.addActionListener(this);
		newGame.setActionCommand("newGame");
		newGame.setEnabled(true);

		// label is the interface between the computer and the user.
		// Messages are displayed with this JLabel.
		label = new JLabel("Welcome to Checkers.", JLabel.CENTER);

		// endGame allows users quit in the middle of a game.
		// It is disabled until the user starts a new game.
		endGame = new JButton("End Game");
		endGame.addActionListener(this);
		endGame.setActionCommand("endGame");
		endGame.setEnabled(false);
		
		// Set constraints for newGame.
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridwidth = 2;
		c1.gridheight = 1;
		c1.gridx = 0;
		c1.gridy = 0;
		c1.weightx = 0.2;
		c1.fill = GridBagConstraints.HORIZONTAL;

		// Set constraints for label.
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridwidth = 4;
		c2.gridheight = 1;
		c2.gridx = 2;
		c2.gridy = 0;
		c2.weightx = 0.9;

		// Set constraints for endGame.
		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridwidth = 2;
		c3.gridheight = 1;
		c3.gridx = 6;
		c3.gridy = 0;
		c3.weightx = 0.2;
		c3.fill = GridBagConstraints.HORIZONTAL;

		// Add the components to UserInteface with their respective constraints.
		add(newGame, c1);
		add(label, c2);
		add(endGame, c3);
	}

	// Action performed calls the method newGame() if newGame is pressed 
	// and calls the method endGame() if endGame is pressed.
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "newGame") {
			newGame();
		}
		if (e.getActionCommand() == "endGame") {
			endGame();
		}
	}

	// newGame() sets up the board so the user can begin playing the game.
	void newGame() {
		Board.setupBoard();
		BoardGraphics.setIcons();
		Panel.setPlayer(Board.WHITE);
		BoardGraphics.setLegalMoves(Board.getLegalMoves(Board.WHITE));
		Panel.setSelectedRow(-1);   // White has not yet selected a piece to move.
		BoardGraphics.setBorders();
		label.setText("White: It's your turn to move.");
		Panel.setPlaying(true);
		newGame.setEnabled(false);
		endGame.setEnabled(true);
	}

	// endGame() clears the board and calls gameOver().
	void endGame() {
		Board.clearBoard();
		BoardGraphics.setIcons();
		BoardGraphics.setBorderPaintedFalse();
		if (Panel.getPlayer() == Board.WHITE) {
			gameOver("White quits. Black wins.");
		}
		else {
			gameOver("Black quits. White wins.");
		}
	}

	// gameOver() ends the game, changes the label,
	// and makes it so a user can begin a new game.
	public static void gameOver(String str) {
		label.setText(str);
		newGame.setEnabled(true);
		endGame.setEnabled(false);
		Panel.setPlaying(false);
	}

	// setLabel() allows the BoardGraphics class to change the label text.
	public static void setLabel(String s) {
		label.setText(s);
	}

}
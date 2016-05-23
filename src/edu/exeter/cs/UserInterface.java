package edu.exeter.cs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class UserInterface extends JPanel implements ActionListener {

	private static JButton newGame;
	private static JLabel label;
	private static JButton endGame;
	private static boolean playing;

	public UserInterface() {
		setLayout(new GridBagLayout());
		setBackground(Color.gray);
		setPreferredSize(new Dimension(700, 25));

		newGame = new JButton("New Game");
		newGame.addActionListener(this);
		newGame.setActionCommand("newGame");
		newGame.setEnabled(true);

		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridwidth = 2;
		c1.gridheight = 1;
		c1.gridx = 0;
		c1.gridy = 0;
		c1.weightx = 0.2;
		c1.fill = GridBagConstraints.HORIZONTAL;


		label = new JLabel("Welcome to Checkers", JLabel.CENTER);

		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridwidth = 4;
		c2.gridheight = 1;
		c2.gridx = 2;
		c2.gridy = 0;
		c2.weightx = 0.9;


		endGame = new JButton("End Game");
		endGame.addActionListener(this);
		endGame.setActionCommand("endGame");
		endGame.setEnabled(false);

		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridwidth = 2;
		c3.gridheight = 1;
		c3.gridx = 6;
		c3.gridy = 0;
		c3.weightx = 0.2;
		c3.fill = GridBagConstraints.HORIZONTAL;

		add(newGame, c1);
		add(label, c2);
		add(endGame, c3);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "newGame") {
			doNewGame();
		}
		if (e.getActionCommand() == "endGame") {
			doEndGame();
		}
	}

	void doNewGame() {
		Board.setupBoard();   // Set up the pieces.
		BoardGraphics.setIcons();
		Panel.setPlayer(Board.WHITE);;   // WHITE moves first.
		BoardGraphics.setLegalMoves(Board.getLegalMoves(Board.WHITE));  // Get WHITE's legal moves.
		Panel.setSelectedRow(-1);   // WHITE has not yet selected a piece to move.
		BoardGraphics.setBorders();
		label.setText("WHITE:  Make your move.");
		playing = true;
		newGame.setEnabled(false);
		endGame.setEnabled(true);
	}


	void doEndGame() {
		// Current player resigns.  Game ends.  Opponent wins.
		if (playing == false) {
			label.setText("There is no game in progress!");
			return;
		}
		if (Panel.getPlayer() == Board.WHITE)
			gameOver("WHITE resigns.  BLACK wins.");
		else
			gameOver("BLACK resigns.  WHITE wins.");
	}

	public static void gameOver(String str) {
		// The game ends.  The parameter, str, is displayed as a message
		// to the user.  The states of the buttons are adjusted so players
		// can start a new game.
		label.setText(str);
		newGame.setEnabled(true);
		endGame.setEnabled(false);
		playing = false;
	}

	public static void setLabel(String s) {
		label.setText(s);
	}
	
}

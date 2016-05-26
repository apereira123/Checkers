package edu.exeter.cs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("serial")
public class BoardGraphics extends JPanel implements ActionListener {

	private static JButton[][] buttons = new JButton[8][8]; // Declare and define a 2-Dimensional array of buttons.
	private static ArrayList<Move> legalMoves; // Declare an ArrayList of Move.
	private Scanner scan; // Declare a scanner which will get the row and col from the button press.
	private static int row; // Declare a variable to store the row of the button press.
	private static int col; // Declare a variable to store the col of the button press.

	
	public BoardGraphics() {
		//setup panel
		setLayout(new GridLayout(8,8));
		setBackground(Color.gray);
		setPreferredSize(new Dimension(700, 700));

		//create buttons
		createButtons();

		//set background color
		setBackground();

		//add buttons
		addButtons();

		//draw buttons
		setIcons();
	}

	// Makes a new button at each coordinate in the array of buttons.
	public void createButtons() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				buttons[i][j] = new JButton();
			}
		}
	}

	// Creates the alternating color pattern of the checker board.
	public void setBackground() {
		String s;
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (row%2 == 0 && col%2 == 0) {
					s = row + " " + col;
					buttons[row][col].setBackground(Color.lightGray);
					buttons[row][col].addActionListener(this); // Add an action listener to legal spaces.
					buttons[row][col].setActionCommand(s); // Set the action command to the coordinates of the button.
				} else if (row%2 == 1 && col%2 == 1) {
					s = row + " " + col;
					buttons[row][col].setBackground(Color.lightGray);
					buttons[row][col].addActionListener(this); // Add an action listener to legal spaces.
					buttons[row][col].setActionCommand(s); // Set the action command to the coordinates of the button.
				} else {
					buttons[row][col].setBackground(Color.black);
				}
			}
		}
	}

	// Adds the buttons to the panel.
	public void addButtons() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				add(buttons[row][col]);
			}
		}
	}

	// Every time a move is made, this method updates the button icons to reflect the move.
	public static void setIcons() {
		// Here we set the ImageIcons and scale them to the correct size for the board.
		ImageIcon whitePawn = new ImageIcon("resources/white_pawn.png");
		whitePawn.setImage(whitePawn.getImage().getScaledInstance(75,75,  java.awt.Image.SCALE_SMOOTH ));
		ImageIcon blackPawn = new ImageIcon("resources/black_pawn.png");
		blackPawn.setImage(blackPawn.getImage().getScaledInstance(75,75,  java.awt.Image.SCALE_SMOOTH ));
		ImageIcon whiteKing = new ImageIcon("resources/white_king.png");
		whiteKing.setImage(whiteKing.getImage().getScaledInstance(75,75,  java.awt.Image.SCALE_SMOOTH ));
		ImageIcon blackKing = new ImageIcon("resources/black_king.png");
		blackKing.setImage(blackKing.getImage().getScaledInstance(75,75,  java.awt.Image.SCALE_SMOOTH ));

		// Here we check the piece at each position on the board and 
		// set the Icon of the each button to the corresponding ImageIcon.
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				buttons[row][col].setOpaque(true);
				if (Board.getPiece(row, col) == 1) {
					buttons[row][col].setIcon(whitePawn);
				}
				else if (Board.getPiece(row, col) == 2) {
					buttons[row][col].setIcon(blackPawn);
				}
				else if (Board.getPiece(row, col) == 3) {
					buttons[row][col].setIcon(whiteKing);
				}
				else if (Board.getPiece(row, col) == 4) {
					buttons[row][col].setIcon(blackKing);
				} 
				else {
					buttons[row][col].setIcon(null);
				}
			}
		}
	}

	// This method draws a border around legal moves.
	public static void setBorders() {
		// Here we highlight pieces that have legal moves.
		if (Panel.selectedRow == -1) {
			for (int i = 0; i < legalMoves.size(); i++) {
				buttons[legalMoves.get(i).fromRow][legalMoves.get(i).fromCol].setBorder(new LineBorder(new Color(0, 128, 255), 5));
			} 
		}
		
		// After a piece is selected, we highlight the possible moves that piece can make.
		else {
			for (int i = 0; i < legalMoves.size(); i++) {
				if (Panel.getSelectedRow() == legalMoves.get(i).fromRow && Panel.getSelectedCol() == legalMoves.get(i).fromCol) {
					buttons[legalMoves.get(i).toRow][legalMoves.get(i).toCol].setBorder(new LineBorder(new Color(0, 128, 255), 5));
				}
			} 
		}
	}

	// The actionPerformed() method looks for a button press and
	// sets row and col to the location of the button press.
	public void actionPerformed(ActionEvent e) {
		scan = new Scanner(e.getActionCommand());
		row = scan.nextInt();
		col = scan.nextInt();
		try {
			doClickSquare(row, col);
		}
		catch (Exception NullPointerException) {}
	}

	// Clicks a square and calls doMakeMove().
	void doClickSquare(int row, int col) {
		// When a user starts, this will prompt them to start
		// a new game if they click on the empty squares.
		if (!Panel.isPlaying()) {
			UserInterface.setLabel("Please start a new game.");
			return;
		}
		
		// Highlight pieces which have legal moves.
		setBorders();

		// If the player clicked on one of the pieces that the player
		// can move, this will mark the row and col as selected and return.
		for (int i = 0; i < legalMoves.size(); i++) {
			if (legalMoves.get(i).fromRow == row && legalMoves.get(i).fromCol == col) {
				Panel.setSelectedRow(row);
				Panel.setSelectedCol(col);;
				if (Panel.getPlayer() == Board.WHITE) {
					UserInterface.setLabel("White: It's your turn to move.");
				}
				else {
					UserInterface.setLabel("Black: It's your turn to move.");
				}
				
				// If the move was legal, this redraws the borders to highlight possible moves.
				setBorders();
				return;
			}
		}
		
		// If no piece has been selected to be moved, the user must first
        // select a piece. Prompt the user to do so and try again.
		if (Panel.getSelectedRow() < 0) {
			UserInterface.setLabel("Click the piece you want to move.");
			return;
		}

		// If the user clicked on a square where the selected piece
		// can be legally moved, then make the move and return.
		for (int i = 0; i < legalMoves.size(); i++) {
			if (legalMoves.get(i).fromRow == Panel.getSelectedRow() && legalMoves.get(i).fromCol == Panel.getSelectedCol() && legalMoves.get(i).toRow == row && legalMoves.get(i).toCol == col) {
				doMakeMove(legalMoves.get(i));
				return;
			}
		}

		/* If we get to this point, there is a piece selected, and the square where
        * the user just clicked is not one where that piece can be legally moved.
        * Prompt the user to make a legal move. */
		UserInterface.setLabel("Click the square you want to move to.");

	}

	// This is called when the current player has chosen the specified move.
	// It makes the move, and then either ends or continues the game appropriately.
	public void doMakeMove(Move move) {
		// Make the move.
		Board.makeMove(move);

		/* If the move was a jump, it's possible that the player has another
        * jump. Check for legal jumps starting from the square that the player
        * just moved to. If there are any, the player must jump. The same
        * player continues moving. */
		if (move.isJump()) {
			legalMoves = Board.getLegalJumpsFrom(Panel.getPlayer(),move.toRow,move.toCol);
			if (legalMoves != null) {
				if (Panel.getPlayer() == Board.WHITE) {
					UserInterface.setLabel("White: You have to continue jumping.");
				}
				else {
					UserInterface.setLabel("Black: You have to continue jumping.");
				}
				Panel.setSelectedRow(move.toRow); // Since only one piece can be moved, select it.
				Panel.setSelectedCol(move.toCol);
				setIcons();
				setBorders();
				return;
			}
		}

		// Here we end the current player's turn and change to the other player.
        // If the other player has no legal moves, then the game ends.
		if (Panel.getPlayer() == Board.WHITE) {
			Panel.setPlayer(Board.BLACK);
			legalMoves = Board.getLegalMoves(Panel.getPlayer());
			if (legalMoves == null) {
				UserInterface.gameOver("Black cannot move. White wins.");
			}
			else {
				UserInterface.setLabel("Black: It's your turn to move.");
			}	
		}
		else {
			Panel.setPlayer(Board.WHITE);
			legalMoves = Board.getLegalMoves(Panel.getPlayer());
			if (legalMoves == null) {
				UserInterface.gameOver("White cannot move. Black wins.");
			}
			else {
				UserInterface.setLabel("White: It's your turn to move.");
			}
		}

		// Set selectedRow = -1 to record that the player has not yet selected a piece to move.
		Panel.setSelectedRow(-1);

		setIcons();
		setBorders();
	}

	public static void setLegalMoves(ArrayList<Move> moves) {
		legalMoves = moves;
	}

}
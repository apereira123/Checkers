package edu.exeter.cs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("serial")
public class BoardGraphics extends JPanel implements ActionListener {

	private static JButton[][] buttons = new JButton[8][8];
	private static ArrayList<Move> legalMoves;
	private Scanner scan;
	private static int row;
	private static int col;

	public BoardGraphics() {
		setupGraphics();
	}

	public void setupGraphics() {
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

		//set the border not to be painted
		setBorderPaintedFalse();
	}

	public void createButtons() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				buttons[i][j] = new JButton();
			}
		}
	}

	public void setBackground() {
		String s;
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (row%2 == 0 && col%2 == 0) {
					s = row + " " + col;
					buttons[row][col].setBackground(Color.lightGray);
					buttons[row][col].addActionListener(this);
					buttons[row][col].setActionCommand(s);
				} else if (row%2 == 1 && col%2 == 1) {
					s = row + " " + col;
					buttons[row][col].setBackground(Color.lightGray);
					buttons[row][col].addActionListener(this);
					buttons[row][col].setActionCommand(s);
				} else {
					buttons[row][col].setBackground(Color.black);
				}
			}
		}
	}

	public void addButtons() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				add(buttons[row][col]);
			}
		}
	}

	public static void setIcons() {
		ImageIcon whitePawn = new ImageIcon("resources/white_pawn.png");
		whitePawn.setImage(whitePawn.getImage().getScaledInstance(75,75,  java.awt.Image.SCALE_SMOOTH ));
		ImageIcon blackPawn = new ImageIcon("resources/black_pawn.png");
		blackPawn.setImage(blackPawn.getImage().getScaledInstance(75,75,  java.awt.Image.SCALE_SMOOTH ));
		ImageIcon whiteKing = new ImageIcon("resources/white_king.png");
		whiteKing.setImage(whiteKing.getImage().getScaledInstance(75,75,  java.awt.Image.SCALE_SMOOTH ));
		ImageIcon blackKing = new ImageIcon("resources/black_king.png");
		blackKing.setImage(blackKing.getImage().getScaledInstance(75,75,  java.awt.Image.SCALE_SMOOTH ));

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

	public static void setBorderPaintedFalse() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				buttons[row][col].setBorderPainted(false);
			}
		}
	}

	public static void setBorderPaintedTrue() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				buttons[row][col].setBorderPainted(true);
				buttons[row][col].setBorder(null);
			}
		}
	}

	public static void setBorders() {
		setBorderPaintedTrue();
		if (Panel.selectedRow == -1) {
			for (int i = 0; i < legalMoves.size(); i++) {
				buttons[legalMoves.get(i).fromRow][legalMoves.get(i).fromCol].setBorder(new LineBorder(new Color(0, 128, 255), 5));
			} 
		}
		else {
			for (int i = 0; i < legalMoves.size(); i++) {
				if (Panel.getSelectedRow() == legalMoves.get(i).fromRow && Panel.getSelectedCol() == legalMoves.get(i).fromCol) {
					buttons[legalMoves.get(i).toRow][legalMoves.get(i).toCol].setBorder(new LineBorder(new Color(0, 128, 255), 5));
				}
			} 
		}
	}

	public void actionPerformed(ActionEvent e) {
		scan = new Scanner(e.getActionCommand());
		row = scan.nextInt();
		col = scan.nextInt();
		try {
			doClickSquare(row, col);
		}
		catch (Exception NullPointerException) {}
	}

	void doClickSquare(int row, int col) {

		/* If the player clicked on one of the pieces that the player
       can move, mark this row and col as selected and return.  (This
       might change a previous selection.)  Reset the message, in
       case it was previously displaying an error message. */

		if (!Panel.isPlaying()) {
			UserInterface.setLabel("Please start a new game.");
			return;
		}
		
		setBorders();

		for (int i = 0; i < legalMoves.size(); i++)
			if (legalMoves.get(i).fromRow == row && legalMoves.get(i).fromCol == col) {
				Panel.setSelectedRow(row);
				Panel.setSelectedCol(col);;
				if (Panel.getPlayer() == Board.WHITE) {
					UserInterface.setLabel("White: It's your turn to move.");
				}
				else {
					UserInterface.setLabel("Black: It's your turn to move.");
				}
				setBorders();
				return;
			}

		/* If no piece has been selected to be moved, the user must first
       select a piece.  Show an error message and return. */
		if (Panel.getSelectedRow() < 0) {
			UserInterface.setLabel("Click the piece you want to move.");
			return;
		}

		/* If the user clicked on a square where the selected piece can be
       legally moved, then make the move and return. */


		setBorders();

		for (int i = 0; i < legalMoves.size(); i++) {
			if (legalMoves.get(i).fromRow == Panel.getSelectedRow() && legalMoves.get(i).fromCol == Panel.getSelectedCol() && legalMoves.get(i).toRow == row && legalMoves.get(i).toCol == col) {
				doMakeMove(legalMoves.get(i));
				return;
			}
		}

		/* If we get to this point, there is a piece selected, and the square where
       the user just clicked is not one where that piece can be legally moved.
       Show an error message. */

		UserInterface.setLabel("Click the square you want to move to.");

	}  // end doClickSquare()


	public void doMakeMove(Move move) {
		// This is called when the current player has chosen the specified
		// move.  Make the move, and then either end or continue the game
		// appropriately.

		Board.makeMove(move);

		/* If the move was a jump, it's possible that the player has another
       jump.  Check for legal jumps starting from the square that the player
       just moved to.  If there are any, the player must jump.  The same
       player continues moving.
		 */

		if (move.isJump()) {
			legalMoves = Board.getLegalJumpsFrom(Panel.getPlayer(),move.toRow,move.toCol);
			if (legalMoves != null) {
				if (Panel.getPlayer() == Board.WHITE) {
					UserInterface.setLabel("White: You have to continue jumping.");
				}
				else {
					UserInterface.setLabel("Black: You have to continue jumping.");
				}
				Panel.setSelectedRow(move.toRow);  // Since only one piece can be moved, select it.
				Panel.setSelectedCol(move.toCol);
				setIcons();
				setBorders();
				return;
			}
		}

		/* The current player's turn is ended, so change to the other player.
       Get that player's legal moves.  If the player has no legal moves,
       then the game ends. */

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

		/* Set selectedRow = -1 to record that the player has not yet selected
        a piece to move. */

		Panel.setSelectedRow(-1);

		setIcons();
		setBorders();

	}  // end doMakeMove();

	public static void setLegalMoves(ArrayList<Move> moves) {
		legalMoves = moves;
	}

}
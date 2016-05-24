package edu.exeter.cs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Scanner;

@SuppressWarnings("serial")
public class BoardGraphics extends JPanel implements ActionListener {

	private static JButton[][] buttons = new JButton[8][8];
	private static Move[] legalMoves;
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
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i%2 == 0 && j%2 == 0) {
					s = i + " " + j;
					buttons[i][j].setBackground(Color.lightGray);
					buttons[i][j].addActionListener(this);
					buttons[i][j].setActionCommand(s);
				} else if (i%2 == 1 && j%2 == 1) {
					s = i + " " + j;
					buttons[i][j].setBackground(Color.lightGray);
					buttons[i][j].addActionListener(this);
					buttons[i][j].setActionCommand(s);
				} else {
					buttons[i][j].setBackground(Color.black);
				}
			}
		}
	}

	public void addButtons() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				add(buttons[i][j]);
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

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				buttons[i][j].setOpaque(true);
				if (Board.getPiece(i, j) == 1) {
					buttons[i][j].setIcon(whitePawn);
				}
				else if (Board.getPiece(i, j) == 2) {
					buttons[i][j].setIcon(blackPawn);
				}
				else if (Board.getPiece(i, j) == 3) {
					buttons[i][j].setIcon(whiteKing);
				}
				else if (Board.getPiece(i, j) == 4) {
					buttons[i][j].setIcon(blackKing);
				} 
				else {
					buttons[i][j].setIcon(null);
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
			for (int i = 0; i < legalMoves.length; i++) {
				buttons[legalMoves[i].fromRow][legalMoves[i].fromCol].setBorder(new LineBorder(new Color(255, 255, 255), 5));
			} 
		}
		else {
			for (int i = 0; i < legalMoves.length; i++) {
				if (Panel.getSelectedRow() == legalMoves[i].fromRow && Panel.getSelectedCol() == legalMoves[i].fromCol) {
					buttons[legalMoves[i].toRow][legalMoves[i].toCol].setBorder(new LineBorder(new Color(255, 255, 255), 5));
				}
			} 
		}
	}

	public void actionPerformed(ActionEvent e) {
		scan = new Scanner(e.getActionCommand());
		row = scan.nextInt();
		col = scan.nextInt();
		doClickSquare(row, col);
	}

	void doClickSquare(int row, int col) {

		/* If the player clicked on one of the pieces that the player
       can move, mark this row and col as selected and return.  (This
       might change a previous selection.)  Reset the message, in
       case it was previously displaying an error message. */

		setBorders();

		for (int i = 0; i < legalMoves.length; i++)
			if (legalMoves[i].fromRow == row && legalMoves[i].fromCol == col) {
				Panel.setSelectedRow(row);
				Panel.setSelectedCol(col);;
				if (Panel.getPlayer() == Board.WHITE) {
					UserInterface.setLabel("WHITE:  Make your move.");
				}
				else {
					UserInterface.setLabel("BLACK:  Make your move.");
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

		for (int i = 0; i < legalMoves.length; i++) {
			if (legalMoves[i].fromRow == Panel.getSelectedRow() && legalMoves[i].fromCol == Panel.getSelectedCol() && legalMoves[i].toRow == row && legalMoves[i].toCol == col) {
				doMakeMove(legalMoves[i]);
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
					UserInterface.setLabel("WHITE:  You must continue jumping.");
				}
				else {
					UserInterface.setLabel("BLACK:  You must continue jumping.");
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
				UserInterface.gameOver("BLACK has no moves.  WHITE wins.");
			}
			else {
				UserInterface.setLabel("BLACK:  Make your move.");
			}	
		}
		else {
			Panel.setPlayer(Board.WHITE);
			legalMoves = Board.getLegalMoves(Panel.getPlayer());
			if (legalMoves == null) {
				UserInterface.gameOver("WHITE has no moves.  BLACK wins.");
			}
			else {
				UserInterface.setLabel("WHITE:  Make your move.");
			}
		}

		/* Set selectedRow = -1 to record that the player has not yet selected
        a piece to move. */

		Panel.setSelectedRow(-1);

		setIcons();
		setBorders();

	}  // end doMakeMove();

	public static void setLegalMoves(Move[] moves) {
		legalMoves = moves;
	}

}

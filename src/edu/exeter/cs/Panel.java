package edu.exeter.cs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

@SuppressWarnings("serial")
public class Panel extends JPanel {
	
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

@SuppressWarnings("serial")
class UserInterface extends JPanel implements ActionListener {
	
	private JButton newGame;
	private static JLabel label;
	private JButton endGame;
	
	private boolean playing;
	
	public UserInterface() {
		setLayout(new GridBagLayout());
		setBackground(Color.gray);
		setPreferredSize(new Dimension(700, 25));
		
		newGame = new JButton("New Game");
		newGame.addActionListener(this);
		newGame.setActionCommand("newGame");
		
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridwidth = 2;
		c1.gridheight = 1;
		c1.gridx = 0;
		c1.gridy = 0;
		c1.weightx = 0.2;
		c1.fill = GridBagConstraints.HORIZONTAL;
		
		
		label = new JLabel("Stuff", JLabel.CENTER);
		
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridwidth = 4;
		c2.gridheight = 1;
		c2.gridx = 2;
		c2.gridy = 0;
		c2.weightx = 0.9;
		
		
		endGame = new JButton("End Game");
		endGame.addActionListener(this);
		endGame.setActionCommand("endGame");
		
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
	    BoardGraphics.drawButtons();
	    Panel.setPlayer(Board.WHITE);;   // RED moves first.
	    BoardGraphics.setLegalMoves(Board.getLegalMoves(Board.WHITE));  // Get RED's legal moves.
	    Panel.setSelectedRow(-1);   // RED has not yet selected a piece to move.
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
	 
	 void gameOver(String str) {
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

@SuppressWarnings("serial")
class BoardGraphics extends JPanel implements ActionListener {

	private static JButton[][] buttons = new JButton[8][8];
	private static Move[] legalMoves;
	private Scanner scan;
	private int row;
	private int col;

	public BoardGraphics() {
		setupGraphics();
	}

	public void setupGraphics() {
		//setup panel
		setLayout(new GridLayout(8,8));
		setBackground(Color.gray);
		setPreferredSize(new Dimension(700, 700));

		//setup board
		Board.setupBoard();

		//create buttons
		createButtons();

		//set background color
		setBackground();

		//add buttons
		addButtons();

		//draw buttons
		drawButtons();
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

	public static void drawButtons() {
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
				buttons[i][j].setBorderPainted(false);
				buttons[i][j].setBorder(null);
				buttons[i][j].setMargin(new Insets(0, 0, 0, 0));
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

	public void addButtons() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				add(buttons[i][j]);
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
		// This is called by mousePressed() when a player clicks on the
		// square in the specified row and col.  It has already been checked
		// that a game is, in fact, in progress.

		/* If the player clicked on one of the pieces that the player
       can move, mark this row and col as selected and return.  (This
       might change a previous selection.)  Reset the message, in
       case it was previously displaying an error message. */

		for (int i = 0; i < legalMoves.length; i++)
			if (legalMoves[i].fromRow == row && legalMoves[i].fromCol == col) {
				Panel.setSelectedRow(row);
				Panel.setSelectedCol(col);;
				if (Panel.getPlayer() == Board.WHITE)
					 UserInterface.setLabel("WHITE:  Make your move.");
				else
					 UserInterface.setLabel("BLACK:  Make your move.");
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

		for (int i = 0; i < legalMoves.length; i++)
			if (legalMoves[i].fromRow == Panel.getSelectedRow() && legalMoves[i].fromCol == Panel.getSelectedCol()
			&& legalMoves[i].toRow == row && legalMoves[i].toCol == col) {
				doMakeMove(legalMoves[i]);
				return;
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
				if (Panel.getPlayer() == Board.WHITE)
					UserInterface.setLabel("WHITE:  You must continue jumping.");
				else
					UserInterface.setLabel("BLACK:  You must continue jumping.");
				Panel.setSelectedRow(move.toRow);  // Since only one piece can be moved, select it.
				Panel.setSelectedRow(move.toCol);
				BoardGraphics.drawButtons();
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
				//gameOver("BLACK has no moves.  WHITE wins.");
			}
			else if (legalMoves[0].isJump()) {
				UserInterface.setLabel("BLACK:  Make your move.  You must jump.");	
			}
			else {
				UserInterface.setLabel("BLACK:  Make your move.");
			}	
		}
		else {
			Panel.setPlayer(Board.WHITE);
			legalMoves = Board.getLegalMoves(Panel.getPlayer());
			if (legalMoves == null) {
				//gameOver("WHITE has no moves.  BLACK wins.");
			}
			else if (legalMoves[0].isJump()) {
				UserInterface.setLabel("WHITE:  Make your move.  You must jump.");
			}
			else {
				UserInterface.setLabel("WHITE:  Make your move.");
			}
		}

		/* Set selectedRow = -1 to record that the player has not yet selected
        a piece to move. */

		Panel.setSelectedRow(-1);

		/* As a courtesy to the user, if all legal moves use the same piece, then
       select that piece automatically so the use won't have to click on it
       to select it. */

		if (legalMoves != null) {
			boolean sameStartSquare = true;
			for (int i = 1; i < legalMoves.length; i++)
				if (legalMoves[i].fromRow != legalMoves[0].fromRow
				|| legalMoves[i].fromCol != legalMoves[0].fromCol) {
					sameStartSquare = false;
					break;
				}
			if (sameStartSquare) {
				Panel.setSelectedRow(legalMoves[0].fromRow);;
				Panel.setSelectedCol(legalMoves[0].fromCol);;
			}
		}

		/* Make sure the board is redrawn in its new state. */

		BoardGraphics.drawButtons();

	}  // end doMakeMove();
	
	public static void setLegalMoves(Move[] moves) {
		legalMoves = moves;
	}

}

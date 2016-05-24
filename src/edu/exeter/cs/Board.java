package edu.exeter.cs;

import java.util.ArrayList;

public class Board {

	// Create constants to refer to the pieces in the array.
	public static final int
	EMPTY = 0,
	WHITE = 1,
	BLACK = 2,
	WHITE_KING = 3,
	BLACK_KING = 4;

	// Create a 2-Dimensional array of all the pieces on the board.
	private static int[][] board = new int[8][8];

	// Set the corresponding spaces to the values of the piece at that position.
	public static void setupBoard() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if ( row % 2 == col % 2 ) {
					if (row < 3) {
						board[row][col] = BLACK;
					} 
					else if (row > 4) {
						board[row][col] = WHITE;
					}
					else {
						board[row][col] = EMPTY;
					}
				}
				else {
					board[row][col] = EMPTY;
				}
			}
		}
	}

	// Set the entire board to be empty.
	public static void clearBoard() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				board[row][col] = EMPTY;
			}
		}
	}

	// Return the piece at the specified row and column.
	public static int getPiece(int row, int col) {
		return board[row][col];
	}

	// Set the value of the piece at the specified row and column.
	public static void setPiece(int row, int col, int rank) {
		board[row][col] = rank;
	}

	/* If the move is legal, this will make the specified move.
	This method takes care of the logic to move a piece. 
	If the move is a jump, the jumped piece will be removed.
	If the move results in the piece becoming a king, the
	piece is set to a king. */
	public static void makeMove(Move move) {
		board[move.toRow][move.toCol] = board[move.fromRow][move.fromCol];
		board[move.fromRow][move.fromCol] = EMPTY;
		if (move.isJump()) {
			int jumpRow = (move.fromRow + move.toRow) / 2;
			int jumpCol = (move.fromCol + move.toCol) / 2;
			board[jumpRow][jumpCol] = EMPTY;
		}
		if (move.toRow == 0 && board[move.toRow][move.toCol] == WHITE) {
			board[move.toRow][move.toCol] = WHITE_KING;
		}
		if (move.toRow == 7 && board[move.toRow][move.toCol] == BLACK) {
			board[move.toRow][move.toCol] = BLACK_KING;
		}
	}      

	// Returns an ArrayList with all the legal moves for the player on the board.
	// If there are no legal moves the method returns null.
	public static ArrayList<Move> getLegalMoves(int player) {
		int playerKing;
		
		if (player == WHITE) {
			playerKing = WHITE_KING;
		}
		else {
			playerKing = BLACK_KING;
		}

		ArrayList<Move> moves = new ArrayList<Move>();

		// Here we check for jumps first. If there are any legal jumps in any of 
		// the four directions of travel the move is added to the ArrayList moves.
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (board[row][col] == player || board[row][col] == playerKing) {
					if (canJump(player, row, col, row+1, col+1, row+2, col+2)) {
						moves.add(new Move(row, col, row+2, col+2));
					}
					if (canJump(player, row, col, row-1, col+1, row-2, col+2)) {
						moves.add(new Move(row, col, row-2, col+2));
					}
					if (canJump(player, row, col, row+1, col-1, row+2, col-2)) {
						moves.add(new Move(row, col, row+2, col-2));
					}
					if (canJump(player, row, col, row-1, col-1, row-2, col-2)) {
						moves.add(new Move(row, col, row-2, col-2));
					}
				}
			}
		}

		// Next we check for any legal moves and add them to the ArrayList moves.
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (board[row][col] == player || board[row][col] == playerKing) {
					if (canMove(player,row,col,row+1,col+1)) {
						moves.add(new Move(row,col,row+1,col+1));
					}
					if (canMove(player,row,col,row-1,col+1)) {
						moves.add(new Move(row,col,row-1,col+1));
					}
					if (canMove(player,row,col,row+1,col-1)) {
						moves.add(new Move(row,col,row+1,col-1));
					}
					if (canMove(player,row,col,row-1,col-1)) {
						moves.add(new Move(row,col,row-1,col-1));
					}
				}
			}
		}
 
		// If no legal moves are found, we return null, otherwise we return moves.
		if (moves.size() == 0) {
			return null;
		}
		else {
			return moves;
		}
	}

	/* Returns an ArrayList with all the legal jumps that the specified player
	can make starting from the specified row and column. If there are no
	legal jumps null is returned. This method is used when making successive
	jumps, i.e. double-jumping, triple-jumping, etc. The logic is similar to
	getLegalMoves(). */
	public static ArrayList<Move> getLegalJumpsFrom(int player, int row, int col) {
		int playerKing;  // The constant representing a King belonging to player.
		
		if (player == WHITE) {
			playerKing = WHITE_KING;
		}
		else {
			playerKing = BLACK_KING;
		}
		
		ArrayList<Move> moves = new ArrayList<Move>();  // The legal jumps will be stored in this vector.
		
		if (board[row][col] == player || board[row][col] == playerKing) {
			if (canJump(player, row, col, row+1, col+1, row+2, col+2)) {
				moves.add(new Move(row, col, row+2, col+2));
			}
			if (canJump(player, row, col, row-1, col+1, row-2, col+2)) {
				moves.add(new Move(row, col, row-2, col+2));
			}
			if (canJump(player, row, col, row+1, col-1, row+2, col-2)) {
				moves.add(new Move(row, col, row+2, col-2));
			}
			if (canJump(player, row, col, row-1, col-1, row-2, col-2)) {
				moves.add(new Move(row, col, row-2, col-2));
			}
		}
		
		if (moves.size() == 0) {
			return null;
		}
		else {
			return moves;
		}
	}

	/* This is called by the two previous methods to check whether the
	player can legally jump from (r1,c1) to (r3,c3).  It is assumed
	that the player has a piece at (r1,c1), that (r3,c3) is a position
	that is 2 rows and 2 columns distant from (r1,c1) and that 
	(r2,c2) is the square between (r1,c1) and (r3,c3). */
	private static boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3) {
		if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8) {
			return false;  // (r3,c3) is off the board.
		}
		if (board[r3][c3] != EMPTY) {
			return false;  // (r3,c3) already contains a piece.
		}
		if (player == WHITE) {
			if (board[r1][c1] == WHITE && r3 > r1) {
				return false;  // Regular white piece can only move  up.
			}
			if (board[r2][c2] != BLACK && board[r2][c2] != BLACK_KING) {
				return false;  // There is no black piece to jump.
			}
			return true;  // The jump is legal.
		}
		else {
			if (board[r1][c1] == BLACK && r3 < r1) {
				return false;  // Regular black piece can only move down.
			}
			if (board[r2][c2] != WHITE && board[r2][c2] != WHITE_KING) {
				return false;  // There is no white piece to jump.
			}
			return true;  // The jump is legal.
		}
	}

	/* This is called by the getLegalMoves() method to determine whether
	the player can legally move from (r1,c1) to (r2,c2).  It is
	assumed that (r1,r2) contains one of the player's pieces and
	that (r2,c2) is a neighboring square. */
	private static boolean canMove(int player, int r1, int c1, int r2, int c2) {
		if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8) {
			return false;  // (r2,c2) is off the board.
		}
		if (board[r2][c2] != EMPTY) {
			return false;  // (r2,c2) already contains a piece.
		}
		if (player == WHITE) {
			if (board[r1][c1] == WHITE && r2 > r1) {
				return false;  // Regular white piece can only move down.
			}
			return true;  // The move is legal.
		}
		else {
			if (board[r1][c1] == BLACK && r2 < r1) {
				return false;  // Regular black piece can only move up.
			}
			return true;  // The move is legal.
		}
	}

}
package edu.exeter.cs;

public class Move {

	//Declare variables which store the necessary information for a move.
	public int fromRow, fromCol, toRow, toCol;

	public Move(int r1, int c1, int r2, int c2) {
		//Construct a move.
		fromRow = r1;
		fromCol = c1;
		toRow = r2;
		toCol = c2;
	}

	public boolean isJump() {
		//If the move is legal, return true if the move is a jump.
		return (fromRow - toRow == 2 || fromRow - toRow == -2);
	}

}
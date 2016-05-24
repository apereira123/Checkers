package edu.exeter.cs;

public class Move {

	int fromRow, fromCol, toRow, toCol;

	public Move(int r1, int c1, int r2, int c2) {
		//Constructs a move.
		fromRow = r1;
		fromCol = c1;
		toRow = r2;
		toCol = c2;
	}

	public boolean isJump() {
		//Assuming the move is legal, this method returns true if the move is a jump.
		return (fromRow - toRow == 2 || fromRow - toRow == -2);
	}

}

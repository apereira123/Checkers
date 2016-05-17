package edu.exeter.cs;

public class Board {

	private static int[][] board = new int[8][8];
	private static final int EMPTY = 0;
	private static final int BLACK_PAWN = 1;
	private static final int WHITE_PAWN = 2;
	//private static final int BLACK_KING = 3;
	//private static final int WHITE_KING = 4;
	
	public static void resetBoard() {
		
		for (int i = 0; i < 3 ; i++) {
			for (int j = 0; j < 8; j++) {
				if (i%2 == 0 && j%2 == 0 || i%2 == 1 && j%2 == 1) {
					board[i][j] = BLACK_PAWN;
				} else {
					board[i][j] = EMPTY;
				}
			}
		}
		for (int i = 5; i < 8 ; i++) {
			for (int j = 0; j < 8; j++) {
				if (i%2 == 0 && j%2 == 0 || i%2 == 1 && j%2 == 1) {
					board[i][j] = WHITE_PAWN;
				} else {
					board[i][j] = EMPTY;
				}
			}
		}
	}
	
	public static int getPiece(int i, int j) {
		return board[i][j];
	}
	
	public static void setPiece(int i, int j, int n) {
		board[i][j] = n;
	}
	
}

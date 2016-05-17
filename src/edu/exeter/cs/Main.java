package edu.exeter.cs;

import javax.swing.*;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		//Create the frame and add the panel to it
		JFrame frame = new JFrame("Checker Board");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		BoardGraphics board = new BoardGraphics();
		
		frame.getContentPane().add(board);
		frame.pack();
		frame.setVisible(true);
		
		//Sample code to test that the graphics will update
		Thread.sleep(5000);
		Board.setPiece(2, 0, 0);
		Board.setPiece(3, 1, 1);
		
		board.drawButtons();

	}

}

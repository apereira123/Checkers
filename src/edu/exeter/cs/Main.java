package edu.exeter.cs;

import javax.swing.JFrame;

public class Main {

	//Create the frame and add the main panel (Panel) to it.
	public static void main(String[] args) {
		JFrame frame = new JFrame("Checker Board");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		Panel board = new Panel();

		frame.getContentPane().add(board);
		frame.pack();
		frame.setVisible(true);
	}

}
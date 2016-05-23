package edu.exeter.cs;

import javax.swing.*;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		//Create the frame and add the panel to it
		JFrame frame = new JFrame("Checker Board");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		Panel board = new Panel();
		
		frame.getContentPane().add(board);
		frame.pack();
		frame.setVisible(true);

	}

}

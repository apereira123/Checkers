package edu.exeter.cs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

@SuppressWarnings("serial")
public class BoardGraphics extends JPanel implements ActionListener{

	private JButton[][] buttons = new JButton[8][8];
	private Scanner scan;
	private String s;
	private int[] piecePos = new int[2];
	
	public BoardGraphics() {
		//setup panel
		setLayout(new GridLayout(8, 8));
		setBackground(Color.gray);
		setPreferredSize(new Dimension(750, 750));
		
		//setup board
		Board.resetBoard();
		
		//create buttons
		createButtons();
		
		//set background color
		setBackground();
		
		//draw buttons
		drawButtons();
		
		//add buttons
		addButtons();
	}
	
	public int[] getPiecePos() {
		return piecePos;
	}
	
	public void createButtons() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				buttons[i][j] = new JButton();
			}
		}
	}
	
	public void setBackground() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i%2 == 0 && j%2 == 0) {
					s = Integer.toString(i) + " " + Integer.toString(j);
					buttons[i][j].setBackground(Color.lightGray);
					buttons[i][j].addActionListener(this);
					buttons[i][j].setActionCommand(s);
				} else if (i%2 == 1 && j%2 == 1) {
					s = Integer.toString(i) + " " + Integer.toString(j);
					buttons[i][j].setBackground(Color.lightGray);
					buttons[i][j].addActionListener(this);
					buttons[i][j].setActionCommand(s);
				} else {
					buttons[i][j].setBackground(Color.black);
				}
			}
		}
	}
	
	public void drawButtons() {
		ImageIcon blackPawn = new ImageIcon("resources/black_pawn.png");
		blackPawn.setImage(blackPawn.getImage().getScaledInstance(75,75,  java.awt.Image.SCALE_SMOOTH ));
		ImageIcon whitePawn = new ImageIcon("resources/white_pawn.png");
		whitePawn.setImage(whitePawn.getImage().getScaledInstance(75,75,  java.awt.Image.SCALE_SMOOTH ));
		ImageIcon blackKing = new ImageIcon("resources/black_king.png");
		blackKing.setImage(blackKing.getImage().getScaledInstance(75,75,  java.awt.Image.SCALE_SMOOTH ));
		ImageIcon whiteKing = new ImageIcon("resources/white_king.png");
		whiteKing.setImage(whiteKing.getImage().getScaledInstance(75,75,  java.awt.Image.SCALE_SMOOTH ));
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				buttons[i][j].setOpaque(true);
				buttons[i][j].setBorderPainted(false);
				buttons[i][j].setBorder(null);
				buttons[i][j].setMargin(new Insets(0, 0, 0, 0));
				if (Board.getPiece(i, j) == 1) {
					buttons[i][j].setIcon(blackPawn);
				}
				else if (Board.getPiece(i, j) == 2) {
					buttons[i][j].setIcon(whitePawn);
				}
				else if (Board.getPiece(i, j) == 3) {
					buttons[i][j].setIcon(blackKing);
				}
				else if (Board.getPiece(i, j) == 4) {
					buttons[i][j].setIcon(whiteKing);
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
		piecePos[0] = scan.nextInt();
		piecePos[1] = scan.nextInt();
		System.out.println("The position is " + piecePos[0] + ", " + piecePos[1]);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (e.getActionCommand().equals(i + " " + j)) {
					
				}
			}
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (e.getActionCommand().equals(i + " " + j)) {
					
				}
			}
		}
	}
	
}

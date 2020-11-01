package com.game.source.main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Help {

	private String text1 = 
	"Player 1 is on the left, and Player 2 is on the right. Player 1's controls are";
	private String text2 = "'S' and 'W', and Player 2's are 'Up' and 'Down'. Press 'Space' to start point.";
	private String text25 = "Be careful, if your rally is too long the ball can break! (Will play a let)";
	
	private Rectangle menubutton = new Rectangle(50, 20, 200, 50);
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g.create();
		Font ocr = new Font("OCR A Extended", Font.BOLD, 80);
		g.setFont(ocr);
		g.drawString("TennisPong", GameTP.WIDTH - 260, 150);
		Font arial = new Font("arial", Font.PLAIN, 20);
		Font large = new Font("OCR A Extended", Font.BOLD, 30);
		Font large1 = new Font("Arial", Font.BOLD, 30);
		Font a2 = new Font("arial", Font.BOLD, 20);
		g.setFont(arial);
		g.drawString(text1, 150, 220);
		g.drawString(text2, 150, 250);
		g.drawString(text25, 150, 280);
		g.setFont(large);
		g.drawString("About TennisPong", 200, 320);
		g.setFont(arial);
		
		g2d.draw(menubutton);
		
		g.setFont(large1);
		g.drawString("Menu", menubutton.x + 60, menubutton.y + 35);
		
		g.setFont(a2);
		g.drawString("by Josef Sajonz", GameTP.WIDTH + 60, 175);
		
	}
	
}

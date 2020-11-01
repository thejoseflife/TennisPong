package com.game.source.main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SetD {

	private Rectangle menubutton = new Rectangle(50, 20, 200, 50);
	public static Rectangle onebutton = new Rectangle(GameTP.WIDTH - 95, 300, 200, 50);
	public static Rectangle twobutton = new Rectangle(GameTP.WIDTH - 95, 400, 200, 50);
	public static Rectangle threebutton = new Rectangle(GameTP.WIDTH - 95, 500, 200, 50);
	
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g.create();
		
		Font ocr = new Font("OCR A Extended", Font.BOLD, 80);
		Font large1 = new Font("Arial", Font.BOLD, 30);
		Font a2 = new Font("arial", Font.BOLD, 20);
		Font ocr1 = new Font("OCR A Extended", Font.BOLD, 50);
		
		g2d.draw(menubutton);
		g2d.draw(onebutton);
		g2d.draw(twobutton);
		g2d.draw(threebutton);
		g.setFont(ocr1);
		g.drawString("Select Difficulty", 190, 250);
		g.setFont(large1);
		g.drawString("Menu", menubutton.x + 60, menubutton.y + 35);
		g.drawString("Sectional", onebutton.x + 35, onebutton.y + 35);
		g.drawString("National", twobutton.x + 42, twobutton.y + 35);
		g.drawString("Professional", threebutton.x + 10, threebutton.y + 35);
		
		g.setFont(ocr);
		g.drawString("TennisPong", GameTP.WIDTH - 260, 150);
		g.setFont(a2);
		g.drawString("by Josef Sajonz", GameTP.WIDTH + 60, 175);
	}
	
}

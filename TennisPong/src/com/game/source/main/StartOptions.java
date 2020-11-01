package com.game.source.main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class StartOptions {

	private Rectangle menubutton = new Rectangle(50, 20, 200, 50);
	public static Rectangle pvpbutton = new Rectangle(GameTP.WIDTH - 95, 200, 200, 50);
	public static Rectangle pvcbutton = new Rectangle(GameTP.WIDTH - 95, 300, 200, 50);
	
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g.create();
		
		Font ocr = new Font("OCR A Extended", Font.BOLD, 80);
		Font large1 = new Font("Arial", Font.BOLD, 30);
		Font a2 = new Font("arial", Font.BOLD, 20);
		
		g2d.draw(menubutton);
		g2d.draw(pvpbutton);
		g2d.draw(pvcbutton);
		g.setFont(large1);
		g.drawString("Menu", menubutton.x + 60, menubutton.y + 35);
		g.drawString("P1 v P2", pvpbutton.x + 45, pvpbutton.y + 35);
		g.drawString("P1 v C", pvcbutton.x + 45, pvcbutton.y + 35);
		
		g.setFont(ocr);
		g.drawString("TennisPong", GameTP.WIDTH - 260, 150);
		g.setFont(a2);
		g.drawString("by Josef Sajonz", GameTP.WIDTH + 60, 175);
	}
	
}

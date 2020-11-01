package com.game.source.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {
	

	public static Rectangle startbutton = new Rectangle(GameTP.WIDTH - 95, 200, 200, 50);
	public static Rectangle newbutton = new Rectangle(GameTP.WIDTH - 95, 300, 200, 50);
	public static Rectangle helpbutton = new Rectangle(GameTP.WIDTH - 95, 400, 200, 50);
	public static Rectangle quitbutton = new Rectangle(GameTP.WIDTH - 95, 500, 200, 50);

	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g.create();
		
		Font ocr = new Font("OCR A Extended", Font.BOLD, 80);
		Font a1 = new Font("arial", Font.BOLD, 30);
		Font a2 = new Font("arial", Font.BOLD, 20);
		g.setFont(ocr);
		g.drawString("TennisPong", GameTP.WIDTH - 260, 150);
		g2d.draw(startbutton);
		g2d.draw(newbutton);
		g2d.draw(helpbutton);
		g2d.draw(quitbutton);
		
		g.setFont(a1);
		g.drawString("Start", startbutton.x + 60, startbutton.y + 35);
		g.drawString("New Match", newbutton.x + 22, newbutton.y + 35);
		g.drawString("Help", helpbutton.x + 65, helpbutton.y + 35);
		g.drawString("Quit", quitbutton.x + 65, quitbutton.y + 35);
		g.setFont(a2);
		g.drawString("by Josef Sajonz", GameTP.WIDTH + 60, 175);
		g.setColor(Color.WHITE);
	}

}

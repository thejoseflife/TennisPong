package com.game.source.main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class PlayerTP {

	private double y;
	private double x;
	private double velX = 0;
	private double velY = 0;
	private int height = 125;
	private int width = 75;
	public Graphics g1;
	public Graphics g2;
	
	private BufferedImage player;
	
	
	public PlayerTP(double x, double y, GameTP game) {
		this.x = x;
		this.y = y;
		
		SpriteSheetTP ss = new SpriteSheetTP(game.getSpriteSheet());
		
		player = ss.grabPI(16, 0);
	}
	
	public void tick() {
		x+=velX;
		y+=velY;
		
		if(y <= 0) {
			y = 0;
		} else if(y >= (600 - height) + 10) {
			y = (600 - height) + 10;
		}
		
	}

	public void render(Graphics g) {
		Font sariel = new Font("Arial", Font.BOLD, 20);
		g1 = (Graphics2D) g.create();
		g2 = (Graphics2D) g.create();
		g2.setFont(sariel);
		g.drawImage(player, (int)x, (int)y, width, height, null);
		g2.drawString("TennisPong, coded by Josef Sajonz", (GameTP.WIDTH * 2) - 335, (GameTP.HEIGHT * 2) + 2);
		
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setVelX(double velX) {
		this.velX = velX;
	}
	
	public void setVelY(double velY) {
		this.velY = velY;
	}
	
	public int getHeight() {
		return height;
	}
	

	
}

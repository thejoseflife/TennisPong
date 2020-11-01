package com.game.source.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.game.source.main.GameTP;

public class EnemyTP {
	private double y;
	private double x;
	private double velX = 0;
	private double velY = 0;
	private int height = 125;
	private int width = 75;
	
	private BufferedImage enemy;
	
	
	public EnemyTP(double x, double y, GameTP game) {
		this.x = x;
		this.y = y;
		
		SpriteSheetTP ss = new SpriteSheetTP(game.getSpriteSheet());
		
		enemy = ss.grabPI(48, 0);
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

	public void start() {
		
	}
	
	public void render(Graphics g) {
		
		g.drawImage(enemy, (int)x, (int)y, width, height, null);
		
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
	
	public double getVelY() {
		return velY;
	}
	
	public int getHeight() {
		return height;
	}
}

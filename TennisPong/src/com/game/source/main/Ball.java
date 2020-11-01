package com.game.source.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Ball {
	
	private double y;
	private double x;
	private double velX = 0;
	private double velY = 0;
	private int height = 32;
	private int width = 32;
	
	private BufferedImage[]  ball = new BufferedImage[2];
	Animation a;
	
	public Ball(double x, double y, GameTP game) {
		this.x = x;
		this.y = y;
		
		SpriteSheetTP ss = new SpriteSheetTP(game.getSpriteSheet());
		
		ball[0] = ss.grabImage(1, 1, 16, 16);
		ball[1] = ss.grabImage(1, 2, 16, 16);
		
		a = new Animation(3, ball[0], ball[1]);
	}
	
	public void tick() {
		x+=velX;
		y+=velY;
		
		if(this.y >= GameTP.HEIGHT * GameTP.SCALE - 16) {
			this.setVelY(-velY);

		} else if(this.y <= 0) {
			this.setVelY(-velY);

		}
		
		restart();
		
		a.runAnimation();
		
		
	}

	public void render(Graphics g) {
		
		a.drawAnimation(g, x, y, 0);
		
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
	
	public int getVelY() {
		return (int)velY;
	}
	
	public int getVelX() {
		return (int)velX;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void restart() {
		if(this.getX() <= 0) {
			this.setY(GameTP.HEIGHT - 16);
			this.setX(GameTP.WIDTH - 16);
			this.setVelX(0);
			this.setVelY(0);
			GameTP.escore++;
			GameTP.etbscore++;
			GameTP.resetStrings();
			GameTP.hasStarted = true;
			GameTP.shot_count = 0;
		} else if(this.getX() >= GameTP.WIDTH * GameTP.SCALE) {
			this.setY(GameTP.HEIGHT - 16);
			this.setX(GameTP.WIDTH - 16);
			this.setVelX(0);
			this.setVelY(0);
			GameTP.pscore++;
			GameTP.ptbscore++;
			GameTP.resetStrings();
			GameTP.hasStarted = true;
			GameTP.shot_count = 0;
		} else if(GameTP.shot_count > 100) {
			this.setY(GameTP.HEIGHT - 16);
			this.setX(GameTP.WIDTH - 16);
			this.setVelX(0);
			this.setVelY(0);
			GameTP.hasStarted = true;
			GameTP.shot_count = 0;
			GameTP.resetStrings();
			System.out.println("Ball broke!");
		}

	}
	
	public void reset() {
		this.setY(GameTP.HEIGHT - 16);
		this.setX(GameTP.WIDTH - 16);
		this.setVelY(0);
		this.setVelX(0);
	}
}

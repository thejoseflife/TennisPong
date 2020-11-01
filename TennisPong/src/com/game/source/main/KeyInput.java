package com.game.source.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	GameTP game;

	public KeyInput(GameTP game) {
		this.game = game;
	}
	
	public void keyPressed(KeyEvent ke) {
		game.keyPressed(ke);
	}
	
	public void keyReleased(KeyEvent ke) {
		game.keyReleased(ke);
	}
	
}

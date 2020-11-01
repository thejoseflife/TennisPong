package com.game.source.main;

import java.awt.image.BufferedImage;

public class SpriteSheetTP {

		private BufferedImage image;
		
		public SpriteSheetTP(BufferedImage image) {
			this.image = image;
		}
		
		public BufferedImage grabPI(int x, int y) {
			BufferedImage pix = image.getSubimage(x, y, 32, 48);
			return pix;
		}
		
		public BufferedImage grabImage(int col, int row, int width, int height) {
			BufferedImage img = image.getSubimage((col * 16) - 16, (row * 16) - 16, width, height);
			return img;
		}
		
}

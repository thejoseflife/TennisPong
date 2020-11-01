package com.game.source.main;
//TennisPong
//Notes:
//Add CPU mode


import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

public class GameTP extends Canvas implements Runnable, MouseListener {

	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 460;
	public static final int HEIGHT = 300;
	public static final int SCALE = 2;
	public final String TITLE = "TennisPong";
	
	private Rectangle menubutton = new Rectangle(50, 20, 200, 50);
	
	private boolean running = false;
	private Thread thread;
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	public static Random r = new Random();
	public static boolean hasStarted = true;
	public static int shot_count = 0;
	public static int pscore = 0;
	public static int escore = 0;
	public static int pgames = 0;
	public static int egames = 0;
	public static int psets = 0;
	public static int esets = 0;
	public static int ptbscore = 0;
	public static int etbscore = 0;
	public static int tbtotal = ptbscore + etbscore;
	public static int tbint = r.nextInt(2);
	public static int difficulty = 0;

	public static String pad = "40";
	public static String ead = "40";
	public static boolean isAdvantage = false;
	public static boolean isTiebreak = false;
	private static boolean PWon = false;
	private static boolean EWon = false;
	//public static boolean pstringbroken = false;
	//public static boolean estringbroken = false;
	public static String pstring = "";
	public static String estring = "";
	public static boolean pstringb = false;
	public static boolean estringb = false;
	public static boolean isNew = false;
	public static boolean isGameCPU = false;
	
	private enum STATE {
		MENU,
		GAME,
		GAMECPU,
		FINISHED,
		HELP,
		BGAME,
		SETD,
	};
	
	private static STATE state = STATE.MENU;
	
	//Player
	private PlayerTP p;
	//Ball
	private Ball b;
	//Enemy
	private EnemyTP e;
	//Menu
	private Menu menu;
	private Help help;
	private StartOptions so;
	private SetD sd;
	
	//Start functions/methods
	public void init() {
		this.addMouseListener(this);
		requestFocus();
		BufferedImageLoaderTP loader = new BufferedImageLoaderTP();
		try {
			
			spriteSheet = loader.loadImage("/sprite_sheet.png");
			background = loader.loadImage("/background.png");
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		addKeyListener(new KeyInput(this));
		p = new PlayerTP(10, HEIGHT - 48, this);
		b = new Ball(WIDTH - 12, HEIGHT - 2, this);
		e = new EnemyTP((WIDTH * 2) - 68, HEIGHT - 48, this);
		menu = new Menu();
		help = new Help();
		so = new StartOptions();
		sd = new SetD();
	}
	
	
	private synchronized void start() {
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop() {
		if(!running)
			return;
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(running) {			//Game Loop
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				updates++;
				delta--;
			}

			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " Ticks, Fps " + frames);
				updates = 0;
				frames = 0;
				
			}
		}
		stop();
	}
	
	private void tick() {
		
		if(state == STATE.GAME || state == STATE.GAMECPU) {
		p.tick();
		b.tick();
		e.tick();
		}
		
		if(pstringb == false && b.getX() >= p.getX() - 16 && b.getX() + 16 <= p.getX() - 16 + 75 && b.getY() >= p.getY() && b.getY() <= p.getY() + 125) {
			b.setVelX(-b.getVelX() + 1);
			shot_count++;
			System.out.println(shot_count);
			if(shot_count == (r.nextInt(50)) + 1) {
				pstring = breakString("Player 1");
				pstringb = true;
			}
		
		} else if(estringb == false && b.getX() >= e.getX() && b.getX() + 16 <= e.getX() + 75 && b.getY() >= e.getY() && b.getY() <= e.getY() + 125) {
			b.setVelX(-b.getVelX() - 1);
			shot_count++;
			System.out.println(shot_count);
			if(shot_count == r.nextInt(50) + 2){
				if(state == STATE.GAME) {
					estring = breakString("Player 2");
				} else if(state == STATE.GAMECPU) {
					estring = breakString("Comp");
				}
				estringb = true;
			}

		} else if(pstringb == true && b.getX() >= p.getX() - 16 && b.getX() + 16 <= p.getX() - 16 + 75 && b.getY() >= p.getY() && b.getY() <= p.getY() + 125) {
			if(pstringb == true && b.getX() >= p.getX() - 16 && b.getX() + 16 <= p.getX() - 16 + 75 && b.getY() >= p.getY() + 80 && b.getY() <= p.getY() + 125) {
				b.setVelX(-b.getVelX() + 1);
				shot_count++;
				System.out.println(shot_count);

			}
		} else if(estringb == true && b.getX() >= e.getX() && b.getX() + 16 <= e.getX() + 75 && b.getY() >= e.getY() && b.getY() <= e.getY() + 125) {
			if(estringb == true && b.getX() >= e.getX() - 16 && b.getX() + 16 <= e.getX() - 16 + 75 && b.getY() >= e.getY() + 80 && b.getY() <= e.getY() + 125) {
				b.setVelX(-b.getVelX() - 1);
				shot_count++;
				System.out.println(shot_count);
			}
		}
		
		if(b.getVelX() > 20) {
			b.setVelX(20);
		}
		
		if(state == STATE.GAMECPU) {
			if(difficulty == 3) {
				if(b.getY() < e.getY() && b.getY() + 32 < e.getY() + 125) {
					e.setVelY(-4);
				} else if(b.getY() > e.getY() && b.getY() + 32 > e.getY() + 125) {
					e.setVelY(4);
				}
				if(e.getVelY() > 4) {
					e.setVelY(4);
				}
				//e.setVelY(b.getVelY());
			} else if(difficulty == 2) {
				if(b.getY() < e.getY() && b.getY() + 32 < e.getY() + 125) {
					e.setVelY(-3);
				} else if(b.getY() > e.getY() && b.getY() + 32 > e.getY() + 125) {
					e.setVelY(3);
				} else if(b.getY() > e.getY() && b.getY() + 32 < e.getY() + 125) {
					e.setVelY(0);
				}
				if(e.getVelY() > 3) {
					e.setVelY(3);
				}
			} else if(difficulty == 1) {
				if(b.getY() < e.getY() && b.getY() + 32 < e.getY() + 125) {
					e.setVelY(-2);
				} else if(b.getY() > e.getY() && b.getY() + 32 > e.getY() + 125) {
					e.setVelY(2);
				} else if(b.getY() > e.getY() && b.getY() + 32 < e.getY() + 125) {
					e.setVelY(0);
				}
				if(e.getVelY() > 2) {
					e.setVelY(2);
				}
			}
			
		}
		
		addScore();
		
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Font arial = new Font("Arial", Font.BOLD, 30);
		g.setFont(arial);
		Graphics2D g2d = (Graphics2D)g.create();
		
		//*********Images can be drawn here VVVVV*********
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		
		if(state == STATE.GAME || state == STATE.GAMECPU) {
		Font large1 = new Font("Arial", Font.BOLD, 30);
		g2d.setFont(large1);
		g2d.draw(menubutton);
		g.drawString("Menu", menubutton.x + 60, menubutton.y + 35);
		b.render(g);
		p.render(g);
		e.render(g);

		g.drawString("Sets:  " + psets + " - " + esets, WIDTH - 82, 25);
		g.drawString("Games: " + pgames + " - " + egames, WIDTH - 82, 55);
		if(isAdvantage) {
			g.drawString("Score: " + pad + " - " + ead, WIDTH - 82, 85);
		} else if(isTiebreak && isAdvantage == false) { 
			g.drawString("Score: " + ptbscore + " - " + etbscore, WIDTH - 82, 85);
		}else {
			g.drawString("Score: " + pscore + " - " + escore, WIDTH - 82, 85);
			
		}
		
		
		g.drawString(pstring, WIDTH - 450, 30);
		g.drawString(estring, WIDTH + 130, 30);
		
		
		//************************************************
		} else if(state == STATE.MENU) {
			menu.render(g);
		} else if(state == STATE.HELP) {
			help.render(g);
		} else if(state == STATE.FINISHED) {
			if(PWon) {
				g.drawString(win("Player 1"), WIDTH - 100, HEIGHT - 16);

			} else if(EWon && isGameCPU == false) {
				g.drawString(win("Player 2"), WIDTH - 100, HEIGHT - 16);
			} else if(EWon && isGameCPU) {
				g.drawString(win("Computer"), WIDTH - 100, HEIGHT - 16);
			}
			g2d.draw(menubutton);
			g.drawString("Menu", menubutton.x + 60, menubutton.y + 35);
		} else if(state == STATE.BGAME) {
			so.render(g);
		} else if(state == STATE.SETD) {
			sd.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	
	public void keyPressed(KeyEvent ke) {
		int key = ke.getKeyCode();
		
		
		if(state == STATE.GAME || state == STATE.GAMECPU) {
		if(key == KeyEvent.VK_UP) {
			if(state == STATE.GAME) {
			e.setVelY(-5);
			}

		} else if(key == KeyEvent.VK_DOWN) {
			if(state == STATE.GAME) {
			e.setVelY(5);
			}

		} else if(key == KeyEvent.VK_SPACE) {
			if(PWon == false && EWon == false) {
			if(hasStarted) {
				if(((pgames + egames) % 2) == 0) {
					b.setVelX(-r.nextInt(5) - 1);
				} else if(((pgames + egames) % 2) != 0){
					b.setVelX(r.nextInt(5) + 1);
				} else if(isTiebreak) {
					if(tbint == 0) {
						b.setVelX(r.nextInt(5) + 1);
						tbint = r.nextInt(2);
					} else {
						b.setVelX(-r.nextInt(5) - 1);
						tbint = r.nextInt(2);
					}
				}
				if(pscore + escore == 0 || pscore + escore == 30 || pscore + escore == 80 && ((pgames + egames) % 2) == 0) {
					b.setVelY(r.nextInt(5) + 1);
				} else if(pscore + escore == 15 || pscore + escore == 45 || pscore + escore == 70 || pscore + escore == 40 && ((pgames + egames) % 2) == 0) {
					b.setVelY(-r.nextInt(5) - 1);
				} else if(pscore + escore == 0 || pscore + escore == 30 || pscore + escore == 80 && ((pgames + egames) % 2) > 0) {
					b.setVelY(-r.nextInt(5) - 1);
				} else if(pscore + escore == 15 || pscore + escore == 45 || pscore + escore == 70 || pscore + escore == 40 && ((pgames + egames) % 2) > 0) {
					b.setVelY(r.nextInt(5) + 1);
				} else if(isTiebreak && (escore + pscore) % 2 == 0) {
					b.setVelY(-r.nextInt(5) - 1);
				} else if(isTiebreak && (pscore + escore) % 2 > 0) {
					b.setVelY(r.nextInt(5) + 1);
				}
			}
			}
		} else if(key == KeyEvent.VK_W) {
			
			p.setVelY(-5);

		} else if(key == KeyEvent.VK_S) {
			p.setVelY(5);

		}
		}
	}
	
	public void keyReleased(KeyEvent ke) {
		int key = ke.getKeyCode();
		if(key == KeyEvent.VK_SPACE) {
			hasStarted = false;
		}
		p.setVelY(0);
		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
		e.setVelY(0);
		}
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_S) {
			p.setVelY(0);
			}

	}
	
	
	public void addScore() {
		if(pscore == 1) {
			pscore = 15;
		}
		if(pscore == 16) {
			pscore = 30;
		}
		if(pscore == 31) {
			pscore = 40;
		}
		if(escore == 1) {
			escore = 15;
		}
		if(escore == 16) {
			escore = 30;
		}
		if(escore == 31) {
			escore = 40;
		}
		if(pscore == 41 && escore == 40) {
			isAdvantage = true;
			pad = "Ad";
		}
		if(pscore == 40 && escore == 41) {
			isAdvantage = true;
			ead = "Ad";
		}
		if(escore == 41 && isAdvantage == false) {
			escore = 0;
			pscore = 0;
			egames++;

		}
		if(pscore == 41 && isAdvantage == false) {
			pscore = 0;
			escore = 0;
			pgames++;

		}
		if(isAdvantage && escore == 41 && pscore == 41) {
			escore--;
			pscore--;
			ead = "40";
			pad = "40";
			
		}
		if(pscore == 41 && isAdvantage == false) {
			escore = 0;
		}
		if(escore == 41 && isAdvantage == false) {
			pscore = 0;
		}
		if(pscore == 42 && isAdvantage) {
			pgames++;
			pscore = 0;
			escore = 0;
			isAdvantage = false;
		}
		if(escore == 42 && isAdvantage) {
			pscore = 0;
			escore = 0;
			egames++;
			isAdvantage = false;
		}
		if(pgames == 6 && egames == 6 && isTiebreak == false) {
			ptbscore = 0;
			etbscore = 0;
			isTiebreak = true;
		}
		
		if(isTiebreak && (ptbscore - etbscore) > 1 && ptbscore >= 7) {
			ptbscore = 0;
			etbscore = 0;
			psets++;
			pscore = 0;
			escore = 0;
			pgames = 0;
			egames = 0;
		}
		if(isTiebreak && (etbscore - ptbscore) > 1 && etbscore >= 7) {
			ptbscore = 0;
			etbscore = 0;
			esets++;
			pscore = 0;
			escore = 0;
			pgames = 0;
			egames = 0;
		}
		if(psets == 2 && esets <= 1) {
			PWon = true;
			state = STATE.FINISHED;
			
		}
		if(esets == 2 && psets <= 1) {
			EWon = true;
			state = STATE.FINISHED;
		}
		if(egames == 6 && pgames < 6 && pgames != 5) {
			esets++;
			egames = 0;
			escore = 0;
			pgames = 0;
			pscore = 0;
		}
		if(pgames == 6 && egames < 6 && egames != 5) {
			psets++;
			egames = 0;
			escore = 0;
			pgames = 0;
			pscore = 0;
		}
		if(pgames == 7 && egames == 6 && isTiebreak) {
			psets++;
			egames = 0;
			escore = 0;
			pgames = 0;
			pscore = 0;
		}
		if(egames == 7 && pgames == 6 && isTiebreak) {
			esets++;
			egames = 0;
			escore = 0;
			pgames = 0;
			pscore = 0;
		}
		
	}
	
	public static void main(String[] args) {

		GameTP game = new GameTP();
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.start();
	}

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}
	
	
	public static String win(String winner) {
		return winner + " won!";
	}

	@Override
	public void mouseClicked(MouseEvent me) {
			if(state == STATE.MENU) {
				if(me.getX() >= Menu.startbutton.getX() && me.getX() <= Menu.startbutton.getX() + 200 && me.getY() >= Menu.startbutton.getY() && me.getY() <= Menu.startbutton.getY() + 50) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					state = STATE.BGAME;
				}

				if(me.getX() >= Menu.helpbutton.getX() && me.getX() <= Menu.helpbutton.getX() + 200 && me.getY() >= Menu.helpbutton.getY() && me.getY() <= Menu.helpbutton.getY() + 50) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					state = STATE.HELP;
				}

				if(me.getX() >= Menu.newbutton.getX() && me.getX() <= Menu.newbutton.getX() + 200 && me.getY() >= Menu.newbutton.getY() && me.getY() <= Menu.newbutton.getY() + 50) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					isNew = true;
					state = STATE.BGAME;
				}

				if(me.getX() >= Menu.quitbutton.getX() && me.getX() <= Menu.quitbutton.getX() + 200 && me.getY() >= Menu.quitbutton.getY() && me.getY() <= Menu.quitbutton.getY() + 50) {
					System.exit(1);
				}

		}  else if(state == STATE.SETD) {
			if(me.getX() >= 50 && me.getX() <= 250 && me.getY() >= 20 && me.getY() <= 70) {
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				state = STATE.MENU;
			} else if(me.getX() >= SetD.onebutton.getX() && me.getX() <= SetD.onebutton.getX() + 200 && me.getY() >= SetD.onebutton.getY() && me.getY() <= SetD.onebutton.getY() + 50) {
				isGameCPU = true;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				state = STATE.GAMECPU;
				difficulty = 1;
			} else if(me.getX() >= SetD.twobutton.getX() && me.getX() <= SetD.twobutton.getX() + 200 && me.getY() >= SetD.twobutton.getY() && me.getY() <= SetD.twobutton.getY() + 50) {
				isGameCPU = true;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				state = STATE.GAMECPU;
				difficulty = 2;
			} else if(me.getX() >= SetD.threebutton.getX() && me.getX() <= SetD.threebutton.getX() + 200 && me.getY() >= SetD.threebutton.getY() && me.getY() <= SetD.threebutton.getY() + 50) {
				isGameCPU = true;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				state = STATE.GAMECPU;
				difficulty = 3;
			}
		} else if(state == STATE.BGAME) {
			
			if(me.getX() >= StartOptions.pvpbutton.getX() && me.getX() <= StartOptions.pvpbutton.getX() + 200 && me.getY() >= StartOptions.pvpbutton.getY() && me.getY() <= StartOptions.pvpbutton.getY() + 50) {
				
				if(isNew == false) {
				state = STATE.GAME;
				} else if(isNew) {
					restartGame();
					b.reset();
					state = STATE.GAME;
				}
			} else if(me.getX() >= StartOptions.pvcbutton.getX() && me.getX() <= StartOptions.pvcbutton.getX() + 200 && me.getY() >= StartOptions.pvcbutton.getY() && me.getY() <= StartOptions.pvcbutton.getY() + 50) {
				if(isNew == false) {
				state = STATE.SETD;
				} else if(isNew) {
					state = STATE.SETD;
					restartGame();
				}
			}
		}
		if(state == STATE.HELP || state == STATE.GAME || state == STATE.FINISHED || state == STATE.BGAME || state == STATE.GAMECPU) {
			
			if(me.getX() >= 50 && me.getX() <= 250 && me.getY() >= 20 && me.getY() <= 70) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				isGameCPU = false;
				state = STATE.MENU;
				b.reset();
				
			}
		}
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
	public void restartGame() {
		pscore = 0;
		escore = 0;
		pgames = 0;
		egames = 0;
		psets = 0;
		esets = 0;
		ptbscore = 0;
		etbscore = 0;
		pad = "40";
		ead = "40";
		shot_count = 0;
		isAdvantage = false;
		isTiebreak = false;
		PWon = false;
		EWon = false;
		e.setY(HEIGHT - 48);
		p.setY(HEIGHT - 48);
		isNew = false;
	}
	
	public String breakString(String player) {
		return player + " broke a string!";

	}
	public static void resetStrings() {
		pstringb = false;
		estringb = false;
		pstring = ""; 
		estring = "";
	}
	
}

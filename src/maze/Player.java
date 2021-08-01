package maze;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player {
	
	private int lrc_x = 0, lrc_y = 0;
	
	private maze maz;
	
	private double x = 7, w = Main.WIDTH/x, y = Main.HEIGHT/w;
	
	private double dx = (lrc_x * w) + w/4;
	private double dy = (lrc_y * w) + w/4;
	
	private int t = 30;
	
	private int timer = 0;
	
	private double dvx = 0, dvy = 0;
	
	private double add = 2 * w / t;
	
	private double acc = -add/t;
	
	private int dest_x, dest_y;
	
	public Player() {
		
		maz = new maze((int)x, (int)y, (int)w);
		
		dest_x = (int) (Math.random() * (x - 5) + 4);
		dest_y = (int) (Math.random() * (y - 5) + 4);
	}
	public void tick() {
		if(!maz.isDone()) {
			for(int i = 0; i < 10; i++) 
				maz.tick();
		}else {
			
			if(dvx < 0.05 && dvx > -0.05 && dvx != 0) {
				dvx = 0;
			}
			if(dvy < 0.05 && dvy > -0.05 && dvy != 0)
				dvy = 0;
			
			if(dvx == 0 && dvy == 0) {
				dx = lrc_x * w + w/4;
				dy = lrc_y * w + w/4;
			}
			
			dx += dvx;
			dy += dvy;
			
			if(dvx > 0)
				dvx += acc;
			else if(dvx < 0)
				dvx -= acc;
			
			if(dvy > 0)
				dvy += acc;
			else if(dvy < 0)
				dvy -= acc;
			
			if(timer != t) {
				timer++;
			}
		}
	}
	public void draw(Graphics g) {
		if(maz.isDone()) {			
			maz.draw(g);
			g.setColor(Color.BLACK);
			g.drawImage(new ImageIcon(getClass().getResource("/maze_player.png")).getImage(), (int)dx - 5, (int)dy - 5,(int)(3 * w/4), (int) (3 * w/4), null);
			g.drawImage(new ImageIcon(getClass().getResource("/Webp.net-gifmaker.gif")).getImage(),(int) (dest_x * w + w/4) - 5,(int) (dest_y * w + w/4) - 5, (int)(3 * w/4), (int) (3 * w/4), null);
		}else {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Monospace", Font.PLAIN, 27));
			g.drawString("Generating Maze", Main.WIDTH/2 - 100, Main.HEIGHT/2);
		}
	}
	public void KeyPressed(int k) {
		try {
			if(k == KeyEvent.VK_DOWN && maz.getMap().get(lrc_x).get(lrc_y).walls[2] == false && maz.getMap().get(lrc_x).get(lrc_y + 1).walls[0] == false && timer == t) {
				dvy += add;
				lrc_y++;
				timer = 0;
			} else if(k == KeyEvent.VK_UP && maz.getMap().get(lrc_x).get(lrc_y).walls[0] == false && maz.getMap().get(lrc_x).get(lrc_y - 1).walls[2] == false && timer == t) {
				dvy -= add;
				lrc_y--;
				timer = 0;
			} else if(k == KeyEvent.VK_RIGHT && maz.getMap().get(lrc_x).get(lrc_y).walls[1] == false && maz.getMap().get(lrc_x + 1).get(lrc_y).walls[3] == false && timer == t) {
				dvx += add;
				lrc_x++;
				timer = 0;
			} else if(k == KeyEvent.VK_LEFT && maz.getMap().get(lrc_x).get(lrc_y).walls[3] == false && maz.getMap().get(lrc_x - 1).get(lrc_y).walls[1] == false && timer == t) {
				dvx -= add;
				lrc_x--;
				timer = 0;
			}
		}catch(Exception e) {}
		
		if(lrc_x == dest_x && lrc_y == dest_y) {
			x += 1;
			w = Main.WIDTH / x;
			y = Main.HEIGHT/w;
			
			maz = new maze((int)x, (int)y, (int)w);
			
			lrc_x = 0;
			lrc_y = 0;
			
			dx = (lrc_x * w) + w/4;
			dy = (lrc_y * w) + w/4;
			
			dvx = 0;
			dvy = 0;
			
			add = 2 * w / t;
			
			acc = -add/t;

			dest_x = (int) (Math.random() * (x - 5) + 4);
			dest_y = (int) (Math.random() * (y - 5) + 4);
		}
	}
}

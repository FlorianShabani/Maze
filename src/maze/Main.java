package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Screen;

;public class Main extends JPanel implements Runnable,KeyListener{
	/**
	 * 
	 */
	public static final int WIDTH = 840, HEIGHT = WIDTH * 9 / 12;
	private static final long serialVersionUID = 1L;
	private boolean running = false;
	int FPS = 80;
	long targetTime = 1000/FPS;
	
	Player player;
	Thread thread;
	
	public Main() {
		
		addKeyListener(this);
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		
		start();
	}
	
	public void start() {
		player = new Player();
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		long start, elapsed, wait;
		while(running) {
			tick();
			repaint();
			
			start = System.nanoTime();
	
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed/1000000;
			
			if(wait <= 0)
				wait = 5;
			try {
				Thread.sleep(wait);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	private void tick() {
		player.tick();
	};
	
	public void paint(Graphics g) {
		//better graphics(dont touch, dont understand);
				Graphics2D g2d = (Graphics2D) g;
				
				RenderingHints rh
			    = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
			    		RenderingHints.VALUE_ANTIALIAS_ON);
			
				rh.put(RenderingHints.KEY_RENDERING,
			        RenderingHints.VALUE_RENDER_QUALITY);
			
				g2d.setRenderingHints(rh);
		
		super.paint(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		player.draw(g);
	}
	static Screen s;
	public static int width, height = 10;
	public static void main(String args[]) {
		JFrame frame = new JFrame("Maze");
		frame.add(new Main());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		player.KeyPressed(e.getKeyCode());
		if(e.getKeyCode() == 27) {
			s.restoreScreen();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}

package maze;

import java.awt.Color;
import java.awt.Graphics;

public class cell{
	
	private boolean visited = false;
	
	public boolean[] walls = {true, true, true, true};
	
	public static int WIDTH;
	
	public int x, y;
	
	public cell(int x, int y, int width) {
		this.x = x;
		this.y = y;
		WIDTH = width;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void draw(Graphics g) {
		//the cell
		if(visited)
			g.setColor(new Color(0, 100, 255));
		else
			g.setColor(new Color(255, 100, 0));
		
		g.fillRect(x, y, WIDTH, WIDTH);
		
		//walls
		g.setColor(new Color(255, 150, 0));
		if(walls[0])
			g.drawLine(x, y, x + WIDTH, y);
		if(walls[1])
			g.drawLine(x + WIDTH, y, x + WIDTH, y + WIDTH);
		if(walls[2])
			g.drawLine(x, y + WIDTH, x + WIDTH, y + WIDTH);
		if(walls[3])
			g.drawLine(x, y, x, y + WIDTH);
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}

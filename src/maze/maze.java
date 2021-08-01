package maze;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class maze{
	
	private List<cell> history = new LinkedList<cell>();
	
	private ArrayList<ArrayList<cell>> map= new ArrayList<ArrayList<cell>>();
	
	private boolean done = false;
	
	private int x, y;
	
	Random random = new Random();
	
	public boolean isDone() {
		return done;
	}

	public ArrayList<ArrayList<cell>> getMap() {
		return map;
	}

	cell currentCell;
	
	cell previousCell;
	int prevX, prevY;
	int cX = 0, cY = 0;
	
	public maze(int x, int y, int width) {
		this.x = x;
		this.y = y;
		for(int i = 0; i < x; i++) {
			
			ArrayList<cell> newL = new ArrayList<cell>();
			
			for(int j = 0; j < y; j++) {
				newL.add(new cell(i * cell.WIDTH, j * cell.WIDTH, width));
			}
			map.add(newL);
		}
		currentCell = map.get(0).get(0);
		
		currentCell.setVisited(true);
	}
	
	public void draw(Graphics g) {
		for(int i = 0; i < map.size(); i++) {
			for(int j = 0; j < map.get(i).size(); j++) {
				map.get(i).get(j).draw(g);
			}
		}
		if(!done) {
			g.setColor(Color.green);
			g.fillRect(cX * cell.WIDTH, cY * cell.WIDTH, cell.WIDTH, cell.WIDTH);
		}
	}
	
	public void tick() {
		if(!done) {
			
			List<cell> neighbours = checkNeighbours(cX, cY);
			
			cell nextCell;
			
			if(neighbours.size() == 0) {
				if(history.size() != x * y - 1) {
					
					currentCell = getRandom(history);
					for(int i = 0; i < map.size(); i++) {
						if(map.get(i).contains(currentCell))
							cX = i;
					}	
					cY = map.get(cX).indexOf(currentCell);
				}else {
					done = true;
				}
			}else {
				nextCell = neighbours.get(random.nextInt(neighbours.size()));
				nextCell.setVisited(true);
				prevX = cX;
				prevY = cY;
				
				previousCell = currentCell;
				currentCell = nextCell;
				history.add(previousCell);
				
				for(int i = 0; i < map.size(); i++) {
					if(map.get(i).contains(currentCell))
						cX = i;
				}
				
				cY = map.get(cX).indexOf(currentCell);
					
				//walls
				if(cY - 1 == prevY) {
					previousCell.walls[2] = false;
					currentCell.walls[0] = false;
				}else if(cY + 1 == prevY) {
					previousCell.walls[0] = false;
					currentCell.walls[2] = false;
				}else if(cX - 1 == prevX) {
					previousCell.walls[1] = false;
					currentCell.walls[3] = false;
				}else if(cX + 1 == prevX) {
					previousCell.walls[3] = false;
					currentCell.walls[1] = false;
				}
			}
		}
	}
	public List<cell> checkNeighbours(int cX, int cY) {
		
		List<cell> neighbours = new LinkedList<cell>();
		
		try {
			if(!map.get(cX - 1).get(cY).isVisited()) {
				neighbours.add(map.get(cX - 1).get(cY));
			}	
		}catch(Exception e) {}
			
		try {
			if(!map.get(cX).get(cY - 1).isVisited()) {
				neighbours.add(map.get(cX).get(cY - 1));
			}
		}catch(Exception e) {}
		
		try {
			if(!map.get(cX + 1).get(cY).isVisited()) {
				neighbours.add(map.get(cX + 1).get(cY));
			}
		}catch(Exception e) {}
	
		try {
			if(!map.get(cX).get(cY + 1).isVisited()) {
				neighbours.add(map.get(cX).get(cY + 1));
			}
		}catch(Exception e) {}
		
		return neighbours;
	}
	public cell getRandom(List<cell> c) {
		
		cell rand;
		
		do {
			rand =  c.get(random.nextInt(c.size()));
		}
		while(checkNeighbours(rand.getX() / cell.WIDTH, rand.getY() / cell.WIDTH).size() == 0);
		
		return rand;
	}
}
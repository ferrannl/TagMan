package model;

public class Wall extends GameObject {
	
	//Constants
	private static final int WALL_WIDTH = 60;
	private static final int WALL_HEIGHT = 350;
	
	public Wall(int xPos, int yPos) {
		this.width = WALL_WIDTH;
		this.height = WALL_HEIGHT;
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public Wall(int width, int height, int xPos, int yPos) {
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	
	

}

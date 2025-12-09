package model;

import java.util.Random;

public class Dash extends GameObject {

	// Instance variables
	private int speed;
	private Random r;
	private int delay;

	// Constants
	private final int DASH_WIDTH = 15;
	private final int DASH_HEIGHT = 50;
	public static final int BEGIN_YPOS = 0;
	private final int MIN_SPEED = 2;
	private final int MAX_SPEED = 7;

	public Dash(int xPos) {
		r = new Random();
		speed = r.nextInt(MAX_SPEED) + MIN_SPEED;
		this.width = DASH_WIDTH;
		this.height = DASH_HEIGHT;
		this.yPos = BEGIN_YPOS;
		this.xPos = xPos;
	}

	public int getSpeed() {
		return speed;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	


}

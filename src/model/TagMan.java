package model;

public class TagMan extends GameObject {
	
	//Instance Variables
	private boolean finished;
	private boolean hit;

	//Constants
	private final int MAN_WIDTH = 50;
	private final int MAN_HEIGHT = 50;
	public static final int SPEED = 5;
	public static final int BEGIN_XPOS = 5;
	public static final int BEGIN_YPOS = 375;

	public TagMan() {
		finished = false;
		this.setWidth(MAN_WIDTH);
		this.setHeight(MAN_HEIGHT);
		this.xPos = BEGIN_XPOS;
		this.yPos = BEGIN_YPOS;
	}

	public void moveTagMan(String dir) {

		if (dir.equals("right")) {
			this.setxPos(this.getxPos() + SPEED);
		} else if (dir.equals("down")) {
			this.setyPos(this.getyPos() + SPEED);
		} else if (dir.equals("up")) {
			this.setyPos(this.getyPos() - SPEED);
		} else if (dir.equals("upright")) {
			this.setxPos(this.getxPos() + ((SPEED / 3) * 2));
			this.setyPos(this.getyPos() - ((SPEED / 3) * 2));
		} else if (dir.equals("downright")) {
			this.setxPos(this.getxPos() + ((SPEED / 3) * 2));
			this.setyPos(this.getyPos() + ((SPEED / 3) * 2));
		}

	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}
	
	
	

}

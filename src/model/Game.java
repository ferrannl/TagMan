package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;

import controller.MainController;
import controller.TimeController;
import view.PlayView;

public class Game extends Observable implements Runnable {

	// Instance variables
	private TagMan tagMan;
	private Thread gameThread;
	private MainController mainController;

	private ArrayList<Wall> walls;
	private ArrayList<Dash> dashes;
	private int gameNumber;
	private int gameScore;
	private boolean inGame;
	private boolean end;
	private int[] intDelays = new int[] { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 };
	private final int FIRST_DASH_XPOS = 180;
	private final int DASH_SPACING = 100;
	private final int DASH_AMOUNT = 10;

	// Constants
	public final int MARGIN = 10;
	public static final int FIRST_LEVEL = 1;

	public Game(MainController mainController) {
		this.inGame = true;
		this.end = false;
		this.gameNumber = FIRST_LEVEL;
		this.mainController = mainController;
		walls = new ArrayList<Wall>();
		tagMan = new TagMan();
		dashes = new ArrayList<Dash>();
		this.createObjects();
		gameThread = new Thread(this);
	}

	/**
	 * Create all objects
	 */
	public void createObjects() {
		this.createCornerWalls();
		this.createDashes();
	}

	public void createCornerWalls() {
		walls.add(new Wall(0, 0));
		walls.add(new Wall(0, 450));
		walls.add(new Wall(1240, 0));
		walls.add(new Wall(1240, 450));

	}

	public void createMiddleWall() {
		walls.add(new Wall(20, 180, 445, 310));
	}

	public void createDashes() {
		int index;
		Random random = new Random();
		for (int i = intDelays.length - 1; i > 0; i--) {
			index = random.nextInt(i + 1);
			if (index != i) {
				intDelays[index] ^= intDelays[i];
				intDelays[i] ^= intDelays[index];
				intDelays[index] ^= intDelays[i];
			}
		}
		int j = FIRST_DASH_XPOS;
		for (int i = 0; i < DASH_AMOUNT; i++) {
			dashes.add(new Dash(j));
			j = j + DASH_SPACING;
			dashes.get(i).setDelay(intDelays[i]);
		}
	}

	public void moveDashes() {
		if (!tagMan.isHit()) {
			for (int i = 0; i < dashes.size(); i++) {
				if (dashes.get(i).getDelay() == 0) {
					Rectangle dash = new Rectangle(dashes.get(i).getxPos(), dashes.get(i).getyPos(),
							dashes.get(i).getWidth(), dashes.get(i).getHeight());
					Rectangle tagManRect = new Rectangle(tagMan.getxPos(), tagMan.getyPos(), tagMan.getWidth(),
							tagMan.getHeight());
					int speed = dashes.get(i).getSpeed();
					int currentY = dashes.get(i).getyPos();
					dashes.get(i).setyPos(currentY + speed);
					if (dash.intersects(tagManRect)) {
						mainController.getTimeController().setPaused(true);
						inGame = false;
						tagMan.setHit(true);
						this.setChanged();
						this.notifyObservers(this);
						return;
					}
				} else {
					dashes.get(i).setDelay(dashes.get(i).getDelay() - 1);
				}
			}
		}
	}

	public void moveTagMan(String dir) {
		if (!tagMan.isFinished() && !tagMan.isHit()) {
			int nextXPos = tagMan.getxPos();
			int nextYPos = tagMan.getyPos();

			for (Wall w : walls) {
				Rectangle rect = new Rectangle(w.getxPos(), w.getyPos(), w.getWidth(), w.getHeight());
				Rectangle tagManRect = new Rectangle(tagMan.getxPos(), tagMan.getyPos(), tagMan.getWidth(),
						tagMan.getHeight());
				if (dir.equals("right")) {
					nextXPos = tagMan.getxPos() + TagMan.SPEED;
				} else if (dir.equals("down")) {
					nextYPos = tagMan.getyPos() + TagMan.SPEED;
				} else if (dir.equals("up")) {
					nextYPos = tagMan.getyPos() - TagMan.SPEED;
				} else if (dir.equals("upright")) {
					nextYPos = tagMan.getyPos() - ((TagMan.SPEED / 3) * 2);
					nextXPos = tagMan.getxPos() + ((TagMan.SPEED / 3) * 2);
				} else if (dir.equals("downright")) {
					nextYPos = tagMan.getyPos() + ((TagMan.SPEED / 3) * 2);
					nextXPos = tagMan.getxPos() + ((TagMan.SPEED / 3) * 2);
				}
				tagManRect.x = nextXPos;
				tagManRect.y = nextYPos;
				if (nextYPos < 0 || nextYPos > (PlayView.SCREEN_HEIGHT - tagMan.getHeight())) {
					return;
				}
				if (rect.intersects(tagManRect)) {
					return;
				}
				if (tagManRect.x + tagMan.getWidth() + MARGIN >= PlayView.SCREEN_WIDTH) {
					Collections.shuffle(dashes);
					mainController.getTimeController().setPaused(true);
					this.gameScore += mainController.getTimeController().getTime();
					inGame = false;
					tagMan.setFinished(true);
					this.setChanged();
					this.notifyObservers(this);
					return;
				}

			}
			tagMan.moveTagMan(dir);
			this.setChanged();
			this.notifyObservers(this);
		}
	}

	public ArrayList<Wall> getWalls() {
		return walls;
	}

	public TagMan getTagMan() {
		return tagMan;
	}

	public ArrayList<Dash> getDashes() {
		return dashes;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(48);
				if (inGame) {
					this.moveDashes();
					this.setChanged();
					this.notifyObservers();
				}
			} catch (InterruptedException e1) {
				break;
			}
		}
	}

	public void prepareNextLevel() {
		this.gameNumber++;
		this.resetDashes();
		tagMan.setxPos(TagMan.BEGIN_XPOS);
		tagMan.setyPos(TagMan.BEGIN_YPOS);
		mainController.getTimeController().setTime(TimeController.BEGIN_TIME);
		this.createMiddleWall();
		for (Dash d : dashes) {
			d.setyPos(Dash.BEGIN_YPOS);
		}
		this.setChanged();
		this.notifyObservers();
	}

	public void resetDashes() {
		dashes.clear();
		this.createDashes();
	}

	public int getGameScore() {
		return gameScore;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public void startGame() {
		gameThread.start();
	}

	public int getGameNumber() {
		return gameNumber;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

}

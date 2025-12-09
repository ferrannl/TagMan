package controller;

import java.util.Observable;

public class TimeController extends Observable implements Runnable {

	// Instance variables
	private Thread t1;
	private int time;
	private MainController mainController;
	private boolean paused;

	// Constants
	public static final int BEGIN_TIME = 30;
	private final int END_TIME = 0;

	public TimeController() {
		time = BEGIN_TIME;
		t1 = new Thread(this);

	}

	@Override
	public void run() {
		while (true) {
				try {
					Thread.sleep(1000);
					if (time > END_TIME) {
						if (!paused) {
							time--;
							this.setChanged();
							this.notifyObservers(time);
						}
					}
				} catch (InterruptedException e) {
					break;
				}
		}
	}

	public void startTimer() {
		t1.start();
	}

	public int getTime() {
		return time;
	}

	public MainController getMainController() {
		return mainController;
	}

	public void setTime(int beginTime) {
		this.time = beginTime;
		this.setChanged();
		this.notifyObservers(time);
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

}

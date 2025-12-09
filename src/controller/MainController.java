package controller;

import model.Game;
import view.MainFrame;

public class MainController {

	//Instance Variables
	private MainFrame view;
	private Game model;
	private TimeController timeController;

	public MainController() {

		model = new Game(this);
		timeController = new TimeController();
		view = new MainFrame(this, timeController);
		view.render();
		model.addObserver(view.getContentPane().getPlayView());
		model.addObserver(view.getContentPane().getGameView());
		timeController.addObserver(view.getContentPane().getTimeView());
	}

	public TimeController getTimeController() {
		return timeController;
	}

	public Game getGame() {
		return this.model;
	}
	
	
	
	

}

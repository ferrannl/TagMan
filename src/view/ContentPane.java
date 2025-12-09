package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import controller.MainController;
import controller.TimeController;

@SuppressWarnings("serial")
public class ContentPane extends JPanel {

	// Instance variables
	private TimeView timeView;
	private GameView gameView;
	private PlayView playView;
	private JPanel leftPanel;

	// Constants
	private final int LEFT_PANEL_WIDTH = 100;


	public ContentPane(TimeController timeController, MainController mainController) {
		this.setLayout(new BorderLayout());
		timeView = new TimeView(timeController);
		gameView = new GameView();
		leftPanel = new JPanel(new BorderLayout());
		playView = new PlayView(mainController, timeController);
		leftPanel.add(gameView, BorderLayout.CENTER);
		leftPanel.add(timeView, BorderLayout.SOUTH);
		leftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, PlayView.SCREEN_HEIGHT));
		this.add(leftPanel, BorderLayout.LINE_START);
		this.add(playView, BorderLayout.CENTER);
	}

	public GameView getGameView() {
		return gameView;
	}

	public PlayView getPlayView() {
		return playView;
	}

	public TimeView getTimeView() {
		return timeView;
	}	
	
	
	
	

	

}

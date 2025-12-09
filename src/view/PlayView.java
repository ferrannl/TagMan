package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import controller.MainController;
import controller.TimeController;
import model.Dash;
import model.Game;
import model.TagMan;
import model.Wall;

@SuppressWarnings("serial")
public class PlayView extends JPanel implements Observer {

	// Instance variables
	private Game game;
	private TagMan tagMan;
	private TagManPainterPlain painterPlain;
	private TimeController timeController;

	private String level = "LEVEL ";
	private ArrayList<Wall> walls;
	private ArrayList<Dash> dashes;
	private boolean pressedS = false;
	private boolean pressedL = false;
	private boolean first = true;

	// Constants
	private final Color BACKGROUND_COLOR = new Color(0, 51, 102);
	public static final int SCREEN_WIDTH = 1300;
	public static final int SCREEN_HEIGHT = 800;

	private final Color TEXT_COLOR = Color.YELLOW;
	private final String WELCOME_TEXT = "Welcome to TagMan";
	private final String INSTRUCTION_TEXT = "move with arrows or numpad";
	private final String START_TEXT = "hit S to start";

	private final int FIRST_LINE_HEIGHT = 320;
	private final int SECOND_LINE_HEIGHT = 340;
	private final int THIRD_LINE_HEIGHT = 380;
	private final int FOURTH_LINE_HEIGHT = 400;
	private final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

	private final String BEAT_TEXT = "FINISHED";
	private final String CONTINUE_TEXT = "hit L to continue";

	private final String HIT_TEXT = "TAGMAN IS HIT";
	private final String GAME_OVER_TEXT = "GAME OVER";
	private final String LOST_SCORE_TEXT = "your score: 0";
	private final String EXIT_TEXT = "hit ESC to exit";

	private final String END_TEXT = "JIPPY TagMan reached the end";

	public PlayView(MainController mainController, TimeController timeController) {
		this.game = mainController.getGame();
		painterPlain = new TagManPainterPlain();
		this.timeController = timeController;
		this.walls = game.getWalls();
		this.dashes = game.getDashes();
		this.tagMan = game.getTagMan();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(BACKGROUND_COLOR);
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}

				if (e.getKeyCode() == KeyEvent.VK_S && (!pressedS)) {
					if (!first) {
						mainController.getGame().setInGame(true);
					}
					if (first) {
						timeController.startTimer();
						mainController.getGame().startGame();
						mainController.getGame().setInGame(true);
						first = false;
					}
					tagMan.setHit(false);
					pressedS = true;
					timeController.setPaused(false);
					repaint();
				}

				if (pressedS) {
					if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
						game.moveTagMan("right");
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
						game.moveTagMan("down");
					} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
						game.moveTagMan("up");
					} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
						game.moveTagMan("upright");
					} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
						game.moveTagMan("downright");
					}
				}
				if (tagMan.isFinished() && e.getKeyCode() == KeyEvent.VK_L && (!pressedL)
						&& game.getGameNumber() == 1) {
					pressedL = true;
					timeController.setTime(TimeController.BEGIN_TIME);
					tagMan.setFinished(false);
					game.prepareNextLevel();
					pressedS = false;
					repaint();
				}
				if (tagMan.isFinished() && e.getKeyCode() == KeyEvent.VK_L && game.getGameNumber() == 2
						&& !game.isEnd()) {
					game.setEnd(true);
					repaint();
				}
			}
		});
		this.setFocusable(true);

	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.paintFlash(g);
		this.drawCornerWalls(g);
		this.drawDashes(g);
		painterPlain.paint(g, this, tagMan);
		if (!game.isEnd()) {
			this.paintScore(g);
		} else {
			this.paintGameBeaten(g);
		}
		this.paintGameOver(g);
	}

	public void drawDashes(Graphics g) {
		for (Dash d : dashes) {
			g.setColor(Color.RED);
			g.fillRect(d.getxPos(), d.getyPos(), d.getWidth(), d.getHeight());
			g.setColor(Color.WHITE);
			g.drawRect(d.getxPos(), d.getyPos(), d.getWidth(), d.getHeight());
		}
	}
	
	public void drawCornerWalls(Graphics g) {
		for (Wall w : walls) {
			g.setColor(Color.WHITE);
			g.fillRect(w.getxPos(), w.getyPos(), w.getWidth(), w.getHeight());
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(w.getxPos() + 6, w.getyPos() + 6, w.getWidth() - 12, w.getHeight() - 12);
		}
	}
	private void paintFlash(Graphics g) {
		super.paintComponent(g);
		if (!pressedS) {
			g.setColor(TEXT_COLOR);
			g.setFont(FONT);
			if(game.getGameNumber() == Game.FIRST_LEVEL) {
			int titleWidth = g.getFontMetrics().stringWidth(WELCOME_TEXT);
			g.drawString(WELCOME_TEXT, SCREEN_WIDTH / 2 - titleWidth / 2, FIRST_LINE_HEIGHT);

			titleWidth = g.getFontMetrics().stringWidth(INSTRUCTION_TEXT);
			g.drawString(INSTRUCTION_TEXT, SCREEN_WIDTH / 2 - titleWidth / 2, SECOND_LINE_HEIGHT);
			}
			int titleWidth = g.getFontMetrics().stringWidth(level + game.getGameNumber());
			g.drawString(level + game.getGameNumber(), SCREEN_WIDTH / 2 - titleWidth / 2, THIRD_LINE_HEIGHT);

			titleWidth = g.getFontMetrics().stringWidth(START_TEXT);
			g.drawString(START_TEXT, SCREEN_WIDTH / 2 - titleWidth / 2, FOURTH_LINE_HEIGHT);
		}
	}

	public void paintScore(Graphics g) {
		if (tagMan.isFinished()) {
			String SCORE_TEXT = "your score: " + timeController.getTime();

			g.setColor(TEXT_COLOR);
			g.setFont(FONT);

			int titleWidth = g.getFontMetrics().stringWidth(BEAT_TEXT);
			g.drawString(BEAT_TEXT, SCREEN_WIDTH / 2 - titleWidth / 2, FIRST_LINE_HEIGHT);

			titleWidth = g.getFontMetrics().stringWidth(SCORE_TEXT);
			g.drawString(SCORE_TEXT, SCREEN_WIDTH / 2 - titleWidth / 2, SECOND_LINE_HEIGHT);

			titleWidth = g.getFontMetrics().stringWidth(CONTINUE_TEXT);
			g.drawString(CONTINUE_TEXT, SCREEN_WIDTH / 2 - titleWidth / 2, THIRD_LINE_HEIGHT);
		}
	}

	public void paintGameOver(Graphics g) {
		if (tagMan.isHit()) {
			g.setColor(TEXT_COLOR);
			g.setFont(FONT);
			int titleWidth = g.getFontMetrics().stringWidth(HIT_TEXT);
			g.drawString(HIT_TEXT, SCREEN_WIDTH / 2 - titleWidth / 2, FIRST_LINE_HEIGHT);

			titleWidth = g.getFontMetrics().stringWidth(GAME_OVER_TEXT);
			g.drawString(GAME_OVER_TEXT, SCREEN_WIDTH / 2 - titleWidth / 2, SECOND_LINE_HEIGHT);

			titleWidth = g.getFontMetrics().stringWidth(LOST_SCORE_TEXT);
			g.drawString(LOST_SCORE_TEXT, SCREEN_WIDTH / 2 - titleWidth / 2, THIRD_LINE_HEIGHT);

			titleWidth = g.getFontMetrics().stringWidth(EXIT_TEXT);
			g.drawString(EXIT_TEXT, SCREEN_WIDTH / 2 - titleWidth / 2, FOURTH_LINE_HEIGHT);
		}
	}

	public void paintGameBeaten(Graphics g) {
		if (game.isEnd()) {
			g.setColor(TEXT_COLOR);
			g.setFont(FONT);
			int titleWidth = g.getFontMetrics().stringWidth(END_TEXT);
			g.drawString(END_TEXT, SCREEN_WIDTH / 2 - titleWidth / 2, FIRST_LINE_HEIGHT);

			titleWidth = g.getFontMetrics().stringWidth(GAME_OVER_TEXT);
			g.drawString(GAME_OVER_TEXT, SCREEN_WIDTH / 2 - titleWidth / 2, SECOND_LINE_HEIGHT);

			String TOTAL_TEXT = "total score " + game.getGameScore();
			titleWidth = g.getFontMetrics().stringWidth(TOTAL_TEXT);
			g.drawString(TOTAL_TEXT, SCREEN_WIDTH / 2 - titleWidth / 2, THIRD_LINE_HEIGHT);

			titleWidth = g.getFontMetrics().stringWidth(EXIT_TEXT);
			g.drawString(EXIT_TEXT, SCREEN_WIDTH / 2 - titleWidth / 2, FOURTH_LINE_HEIGHT);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		Game game = (Game) o;
		this.walls = game.getWalls();
		this.dashes = game.getDashes();
		this.tagMan = game.getTagMan();
		this.repaint();
	}

	public Game getGame() {
		return game;
	}

}

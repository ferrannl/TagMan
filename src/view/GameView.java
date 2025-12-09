package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Game;

@SuppressWarnings("serial")
public class GameView extends JPanel implements Observer {

	// Instance Variables
	private JLabel levelLabel;

	private JLabel levelID;
	private JLabel scoreNumber;
	private JLabel scoreLabel;
	private int level;
	private int score;

	// Constants
	private final Color PANEL_COLOR = new Color(30, 40, 60);
	private final Color TEXT_COLOR = Color.YELLOW;
	private final Font TEXT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	private final Font NUMBER_FONT = new Font("Bodoni MT Black", Font.PLAIN, 20);
	private final String LEVEL_TEXT = "level";
	private final String SCORE_TEXT = "score";
	private final Border PANEL_BORDER = BorderFactory.createLineBorder(Color.GRAY, 2);

	public GameView() {
		level = Game.FIRST_LEVEL;
		scoreLabel = new JLabel(SCORE_TEXT);
		scoreNumber = new JLabel();
		levelLabel = new JLabel(LEVEL_TEXT);
		levelID = new JLabel();
		this.setBorder(PANEL_BORDER);
		this.setBackground(Color.BLACK);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.createScorePanel();
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.createScoreNumberPanel();
		this.add(Box.createRigidArea(new Dimension(0, 80)));
		this.createLevelPanel();
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.createLevelNumberPanel();
	}

	public void createScorePanel() {
		scoreLabel.setFont(TEXT_FONT);
		scoreLabel.setBorder(BorderFactory.createEmptyBorder(4, 22, 4, 22));
		scoreLabel.setForeground(TEXT_COLOR);
		scoreLabel.setBackground(PANEL_COLOR);
		scoreLabel.setOpaque(true);
		scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(scoreLabel);
	}

	public void createScoreNumberPanel() {
		scoreNumber.setFont(NUMBER_FONT);
		scoreNumber.setBorder(BorderFactory.createEmptyBorder(4, 22, 4, 22));
		scoreNumber.setForeground(TEXT_COLOR);
		scoreNumber.setBackground(PANEL_COLOR);
		scoreNumber.setOpaque(true);
		scoreNumber.setText(Integer.toString(score));
		scoreNumber.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		this.add(scoreNumber);
	}

	public void createLevelPanel() {
		levelLabel.setFont(TEXT_FONT);
		levelLabel.setBorder(BorderFactory.createEmptyBorder(4, 22, 4, 22));
		levelLabel.setForeground(TEXT_COLOR);
		levelLabel.setBackground(PANEL_COLOR);
		levelLabel.setOpaque(true);
		levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(levelLabel);
	}

	public void createLevelNumberPanel() {
		levelID.setFont(NUMBER_FONT);
		levelID.setBorder(BorderFactory.createEmptyBorder(4, 22, 4, 22));
		levelID.setForeground(TEXT_COLOR);
		levelID.setBackground(PANEL_COLOR);
		levelID.setOpaque(true);
		levelID.setText(Integer.toString(level));
		levelID.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		this.add(levelID);
	}

	@Override
	public void update(Observable o, Object arg) {
		Game game = (Game) o;
		this.score = game.getGameScore();
		this.level = game.getGameNumber();
		scoreNumber.setText(Integer.toString(score));
		levelID.setText(Integer.toString(level));
	}

}

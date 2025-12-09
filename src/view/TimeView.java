package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controller.TimeController;

@SuppressWarnings("serial")
public class TimeView extends JPanel implements Observer {

	@SuppressWarnings("unused")
	private TimeController timeController;
	private int time;
	private JLabel timeLabel = new JLabel();
	private JLabel secondsLabel = new JLabel();

	private final String SECONDS_TEXT = "seconds";
	private final Font TEXT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	private final Font NUMBER_FONT = new Font("Bodoni MT Black", Font.PLAIN, 20);
	private final Border PANEL_BORDER = BorderFactory.createLineBorder(Color.GRAY, 2);
	private final Color TEXT_COLOR = Color.YELLOW;
	private final Color PANEL_COLOR = new Color(30, 40, 60);
	private final int ORANGE_TIME = 15;
	private final int RED_TIME = 7;

	private final int BAR_WIDTH = 35;
	private final int BAR_HEIGHT = 80;

	public TimeView(TimeController timeController) {
		this.timeController = timeController;
		this.time = TimeController.BEGIN_TIME;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.BLACK);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.createTimePanel();
		this.add(Box.createRigidArea(new Dimension(0, 400)));
		this.createSecondsLabel();
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.setBorder(PANEL_BORDER);
	}

	public void createTimePanel() {
		timeLabel.setFont(NUMBER_FONT);
		timeLabel.setBorder(BorderFactory.createEmptyBorder(4, 22, 4, 22));
		timeLabel.setForeground(TEXT_COLOR);
		timeLabel.setBackground(PANEL_COLOR);
		timeLabel.setOpaque(true);
		timeLabel.setText(Integer.toString(time));
		timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(timeLabel, BorderLayout.PAGE_START);
	}

	public void createSecondsLabel() {
		secondsLabel.setFont(TEXT_FONT);
		secondsLabel.setBorder(BorderFactory.createEmptyBorder(2, 14, 2, 14));
		secondsLabel.setForeground(TEXT_COLOR);
		secondsLabel.setBackground(PANEL_COLOR);
		secondsLabel.setOpaque(true);
		secondsLabel.setText(SECONDS_TEXT);
		secondsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(secondsLabel, BorderLayout.PAGE_END);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.drawTimeBar(g);
	}

	public void drawTimeBar(Graphics g) {
		if (time > ORANGE_TIME) {
			g.setColor(Color.BLUE);
		} else if (time <= ORANGE_TIME && time > RED_TIME) {
			g.setColor(Color.ORANGE);
		} else if (time <= RED_TIME) {
			g.setColor(Color.RED);
		}
		g.fillRect(this.getWidth() / 2 - BAR_WIDTH / 2, BAR_HEIGHT + (360 / 30 * (TimeController.BEGIN_TIME - time)),
				BAR_WIDTH, time * 12);
		g.setColor(Color.WHITE);
		g.drawRect(this.getWidth() / 2 - BAR_WIDTH / 2, BAR_HEIGHT + (360 / 30 * (TimeController.BEGIN_TIME - time)),
				BAR_WIDTH, time * 12);

	}

	@Override
	public void update(Observable o, Object arg) {
		this.time = (int) arg;
		timeLabel.setText(Integer.toString(time));
		this.repaint();
	}

}

package view;

import java.awt.Color;
import java.awt.Graphics;

import model.TagMan;

public class TagManPainterPlain {

	// Instance Variables

	// Constants
	private final Color TAGMAN_COLOR_OUTER = new Color(255, 69, 0);
	private final Color TAGMAN_COLOR_MIDDLE = new Color(255, 140, 0);
	private final Color TAGMAN_COLOR_INNER = new Color(255, 200, 0);
	private final Color END_TAGMAN_COLOR_OUTER = new Color(0, 166, 84);
	private final Color END_TAGMAN_COLOR_MIDDLE = new Color(0, 255, 128);
	private final Color END_TAGMAN_COLOR_INNER = new Color(170, 255, 170);
	private final double MIDDLE_POS = 4.99;
	private final double INNER_POS = 12.50;

	public TagManPainterPlain() {

	}

	public void paint(Graphics g, PlayView view, TagMan man) {
		if (!man.isFinished()) {
			g.setColor(TAGMAN_COLOR_OUTER);
			g.fillOval(man.getxPos(), man.getyPos(), man.getWidth(), man.getHeight());
			g.setColor(TAGMAN_COLOR_MIDDLE);
			g.fillOval((int) (man.getxPos() + MIDDLE_POS), (int) (man.getyPos() + MIDDLE_POS), (int) (man.getWidth() / 1.2),
					(int) (man.getHeight() / 1.2));
			g.setColor(TAGMAN_COLOR_INNER);
			g.fillOval((int) (man.getxPos() + INNER_POS), (int) (man.getyPos() + INNER_POS), man.getWidth() / 2,
					man.getHeight() / 2);
		} else {
			g.setColor(END_TAGMAN_COLOR_OUTER);
			g.fillOval(man.getxPos(), man.getyPos(), man.getWidth(), man.getHeight());
			g.setColor(END_TAGMAN_COLOR_MIDDLE);
			g.fillOval((int) (man.getxPos() + MIDDLE_POS), (int) (man.getyPos() + MIDDLE_POS), (int) (man.getWidth() / 1.2),
					(int) (man.getHeight() / 1.2));
			g.setColor(END_TAGMAN_COLOR_INNER);
			g.fillOval((int) (man.getxPos() + INNER_POS), (int) (man.getyPos() + INNER_POS), man.getWidth() / 2,
					man.getHeight() / 2);
		}
	}

}

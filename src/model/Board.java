package model;

import gui.AmazonUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Board {

	private GameObject[][] field;
	private Vector<Point> arrows;
	private Vector<Queen> queens;
	@Setter
	private Point highlight, target;

	/**
	 * Board with field[x][y]
	 */
	public Board() {
		queens = new Vector<Queen>();
		arrows = new Vector<Point>();

		field = new GameObject[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				field[i][j] = GameObject.Empty;
			}
		}
	}

	public void addQueen(Queen q) {
		if (field[q.getPosition().x][q.getPosition().y] != GameObject.AmazonWhite && field[q.getPosition().x][q.getPosition().y] != GameObject.AmazonBlack) {
			queens.add(q);
			if (q.isColor())
				field[q.getPosition().x][q.getPosition().y] = GameObject.AmazonWhite;
			else
				field[q.getPosition().x][q.getPosition().y] = GameObject.AmazonBlack;
		}
	}

	public Queen hasQueen(Point p) {
		for (Queen queen : queens) {
			if (queen.getPosition().equals(p)) {
				return queen;
			}
		}
		return null;
	}

	public boolean isEmpty(Point p) {
		return (field[p.x][p.y] == GameObject.Empty);
	}

	public void move(Queen queen, Point target, Point arrow) {
		field[queen.getPosition().x][queen.getPosition().y] = GameObject.Empty;
		queen.move(target);
		if (queen.isColor())
			field[queen.getPosition().x][queen.getPosition().y] = GameObject.AmazonWhite;
		else
			field[queen.getPosition().x][queen.getPosition().y] = GameObject.AmazonBlack;
		field[arrow.x][arrow.y] = GameObject.Arrow;
		arrows.add(arrow);
	}

	public void draw(Graphics2D g2) {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if ((i + j) % 2 == 0) {
					// g2.setColor(Color.lightGray);
					g2.setColor(new Color(153, 0, 153));
				} else {
					// g2.setColor(Color.darkGray);
					g2.setColor(new Color(255, 51, 153));
				}
				g2.fill(new Rectangle2D.Double((int) (0.5 * AmazonUI.SQUARESIZE) + i * AmazonUI.SQUARESIZE + 1, j * AmazonUI.SQUARESIZE + 1, AmazonUI.SQUARESIZE - 1, AmazonUI.SQUARESIZE - 1));

				if (field[i][j] == GameObject.Arrow) {
					g2.drawImage(AmazonUI.Arrow.getScaledInstance((int) (AmazonUI.SQUARESIZE * .40), (int) (AmazonUI.SQUARESIZE * .8), Image.SCALE_SMOOTH),
							(int) ((0.5 * AmazonUI.SQUARESIZE) + (i + 0.35) * AmazonUI.SQUARESIZE), (int) ((j + 0.1) * AmazonUI.SQUARESIZE + 1), null);
				}

				if (field[i][j] == GameObject.AmazonWhite) {
					g2.drawImage(AmazonUI.Queen_White.getScaledInstance((int) (AmazonUI.SQUARESIZE * .80), (int) (AmazonUI.SQUARESIZE * .8), Image.SCALE_SMOOTH),
							(int) ((0.5 * AmazonUI.SQUARESIZE) + (i + 0.1) * AmazonUI.SQUARESIZE), (int) ((j + 0.1) * AmazonUI.SQUARESIZE + 1), null);
				}

				if (field[i][j] == GameObject.AmazonBlack) {
					g2.drawImage(AmazonUI.Queen_Black.getScaledInstance((int) (AmazonUI.SQUARESIZE * .80), (int) (AmazonUI.SQUARESIZE * .8), Image.SCALE_SMOOTH),
							(int) ((0.5 * AmazonUI.SQUARESIZE) + (i + 0.1) * AmazonUI.SQUARESIZE), (int) ((j + 0.1) * AmazonUI.SQUARESIZE + 1), null);
				}

				if (i == 0) {
					g2.setColor(Color.black);
					g2.setFont(new Font("Calibri", Font.PLAIN, (int) (AmazonUI.SQUARESIZE / 3.5)));
					g2.drawString("" + (char) (j + 97), (int) ((0.95 * AmazonUI.SQUARESIZE) + j * AmazonUI.SQUARESIZE), (int) (10.3 * AmazonUI.SQUARESIZE));
				}
			}

			g2.setColor(Color.black);
			if (i > 0) {
				g2.drawString(" " + (10 - i), (int) (0.15 * AmazonUI.SQUARESIZE), (int) ((0.6 * AmazonUI.SQUARESIZE) + i * AmazonUI.SQUARESIZE));
			} else {
				g2.drawString("" + (10 - i), (int) (0.15 * AmazonUI.SQUARESIZE), (int) ((0.6 * AmazonUI.SQUARESIZE) + i * AmazonUI.SQUARESIZE));
			}
		}

		if (highlight != null) {
			g2.setStroke(new BasicStroke((int) (1 / 11.0 * AmazonUI.SQUARESIZE)));
			g2.setColor(new Color(0, 0, 204));
			g2.draw(new Rectangle2D.Double((int) (0.5 * AmazonUI.SQUARESIZE) + highlight.x * AmazonUI.SQUARESIZE + 1, highlight.y * AmazonUI.SQUARESIZE + 1, AmazonUI.SQUARESIZE - 2,
					AmazonUI.SQUARESIZE - 2));
		}

		if (target != null) {
			g2.drawImage(AmazonUI.Foot_steps.getScaledInstance((int) (AmazonUI.SQUARESIZE * .80), (int) (AmazonUI.SQUARESIZE * .80), Image.SCALE_SMOOTH),
					(int) ((target.x * AmazonUI.SQUARESIZE) + (0.62 * AmazonUI.SQUARESIZE)), (int) ((target.y * AmazonUI.SQUARESIZE) + (0.12 * AmazonUI.SQUARESIZE)), null);
			// g2.setColor(Color.black);
			// g2.fill(new Rectangle2D.Double((int) (0.5 * AmazonUI.SQUARESIZE)
			// + target.x * AmazonUI.SQUARESIZE + 1, target.y *
			// AmazonUI.SQUARESIZE + 1, AmazonUI.SQUARESIZE - 1,
			// AmazonUI.SQUARESIZE - 1));
		}

		// TODO: Make image scale
		// g2.drawImage(AmazonUI.Queen_White.getScaledInstance(AmazonUI.SQUARESIZE,
		// AmazonUI.SQUARESIZE, 0), 100, 100, null);
	}
}

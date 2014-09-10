package model;

import gui.AmazonUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import lombok.Getter;

@Getter
public class Board {

	private GameObject[][] field;
	private Vector<Point> arrows;
	private Vector<Queen> queens;
	private Point highlight;

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

		// TODO: remove
		field[0][4] = GameObject.Arrow;
		field[5][8] = GameObject.Arrow;
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

	public void setHighlight(Point p) {
		highlight = p;
	}

	public void draw(Graphics2D g2) {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if ((i + j) % 2 == 0) {
					g2.setColor(Color.darkGray);
				} else {
					g2.setColor(Color.lightGray);
				}
				g2.fill(new Rectangle2D.Double((int) (0.5 * AmazonUI.SQUARESIZE) + i * AmazonUI.SQUARESIZE + 1, j * AmazonUI.SQUARESIZE + 1, AmazonUI.SQUARESIZE - 1, AmazonUI.SQUARESIZE - 1));

				if (field[i][j] == GameObject.Arrow) {
					g2.setColor(Color.black);
					g2.fill(new Ellipse2D.Double((int) (0.5 * AmazonUI.SQUARESIZE) + (i + 0.25) * AmazonUI.SQUARESIZE, (j + 0.25) * AmazonUI.SQUARESIZE + 1, AmazonUI.SQUARESIZE / 2,
							AmazonUI.SQUARESIZE / 2));
				}

				if (field[i][j] == GameObject.AmazonWhite) {
					g2.setColor(Color.blue);
					g2.fill(new Ellipse2D.Double((int) (0.5 * AmazonUI.SQUARESIZE) + (i + 0.25) * AmazonUI.SQUARESIZE, (j + 0.25) * AmazonUI.SQUARESIZE + 1, AmazonUI.SQUARESIZE / 2,
							AmazonUI.SQUARESIZE / 2));
				}

				if (field[i][j] == GameObject.AmazonBlack) {
					g2.setColor(Color.red);
					g2.fill(new Ellipse2D.Double((int) (0.5 * AmazonUI.SQUARESIZE) + (i + 0.25) * AmazonUI.SQUARESIZE, (j + 0.25) * AmazonUI.SQUARESIZE + 1, AmazonUI.SQUARESIZE / 2,
							AmazonUI.SQUARESIZE / 2));
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
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.orange);
			g2.draw(new Rectangle2D.Double((int) (0.5 * AmazonUI.SQUARESIZE) + highlight.x * AmazonUI.SQUARESIZE + 1, highlight.y * AmazonUI.SQUARESIZE + 1, AmazonUI.SQUARESIZE - 1,
					AmazonUI.SQUARESIZE - 1));
		}

		// TODO: Make image scale
		// g2.drawImage(AmazonUI.Queen_White, null, 100, 100);
	}
}

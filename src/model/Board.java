package model;

import gui.AmazonUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import lombok.Getter;

@Getter
public class Board {

	private GameObject[][] field;

	public Board() {
		field = new GameObject[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				field[i][j] = GameObject.Empty;
			}
		}

		// TODO: remove
		field[0][3] = GameObject.Arrow;
		field[5][8] = GameObject.Arrow;
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

		// TODO: Make image scale
		// g2.drawImage(AmazonUI.Queen_White, null, 100, 100);
	}
}

package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import model.Board;

public class AmazonBoardPanel extends JPanel {

	private static final long serialVersionUID = -5278048525094693808L;
	private Board board;

	public AmazonBoardPanel(Board board) {
		this.board = board;
		setPreferredSize(new Dimension((int) (10.5 * AmazonUI.SQUARESIZE + 1), (int) (10.5 * AmazonUI.SQUARESIZE + 1)));
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.gray);
		g2.fill(getVisibleRect());

		board.draw(g2);
	}
}

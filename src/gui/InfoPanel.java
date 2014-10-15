package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import logic.AmazonLogic;

public class InfoPanel extends JPanel implements Observer {

	private static final long serialVersionUID = -7102795064597828847L;
	private boolean currentTurn;
	private ArrayList<String> information;

	public InfoPanel() {
		setPreferredSize(new Dimension((int) (3 * AmazonUI.SQUARESIZE + 1), (int) (9.5 * AmazonUI.SQUARESIZE + 1)));
		information = new ArrayList<String>();
		currentTurn = true;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.gray);
		g2.fill(getVisibleRect());

		g2.setColor(Color.black);
		g2.setFont(new Font("Calibri", Font.PLAIN, (int) (AmazonUI.SQUARESIZE / 3.5)));
		if (currentTurn)
			g2.drawString("White's turn", (int) ((0.95 * AmazonUI.SQUARESIZE)), (int) (6 * AmazonUI.SQUARESIZE));
		else {
			g2.drawString("Black's turn", (int) ((0.95 * AmazonUI.SQUARESIZE)), (int) (6 * AmazonUI.SQUARESIZE));
		}
	}

	@Override
	public void update(Observable obser, Object obj) {
		currentTurn = ((AmazonLogic) obser).isCurrentTurn();
		this.repaint();
	}
}

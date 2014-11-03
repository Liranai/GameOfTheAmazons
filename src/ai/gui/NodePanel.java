package ai.gui;

import gui.AmazonUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import ai.searchtree.Node;

public class NodePanel extends JPanel {

	private Node node;

	public NodePanel(Node node) {
		this.node = node;
		setPreferredSize(new Dimension((int) (10.5 * AmazonUI.SQUARESIZE + 1), (int) (10.5 * AmazonUI.SQUARESIZE + 1)));
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.gray);
		g2.fill(getVisibleRect());

		// TODO: draw
	}
}

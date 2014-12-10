package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.PriorityQueue;

import javax.swing.JPanel;

import ai.search.AStarTwoNode;

public class NodePanel extends JPanel {

	private static final long serialVersionUID = -8246053719904620256L;
	private PriorityQueue<AStarTwoNode> queue;

	public NodePanel(PriorityQueue<AStarTwoNode> queue) {
		this.queue = queue;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.gray);
		g2.fill(getVisibleRect());

	}
}

package gui;

import java.awt.Dimension;
import java.util.PriorityQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ai.search.AStarTwoNode;

public class NodeViewer extends JFrame {

	private JPanel nodePanel;

	public NodeViewer(PriorityQueue<AStarTwoNode> queue) {
		super("NodeFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension((int) (10.5 * 30 + 1) + 16, (int) (10.5 * 30 + 1) + 41));
		setResizable(true);

		nodePanel = new NodePanel(queue);
		add(nodePanel);
		pack();
		setVisible(true);
	}
}

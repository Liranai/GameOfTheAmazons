package ai;

import java.util.Observable;
import java.util.PriorityQueue;

import logic.AmazonLogic;
import model.Board;
import ai.search.Node;
import ai.search.NodeComparator;

public class AStarAI extends ArtificialIntelligence {

	private Board board;
	private PriorityQueue<Node> queue;

	public AStarAI(boolean color) {
		super(color);
		queue = new PriorityQueue<Node>(10, new NodeComparator());
	}

	@Override
	public void update(Observable obser, Object obj) {
		this.board = ((AmazonLogic) obser).getBoard().clone();

		System.out.println(board.equals(((AmazonLogic) obser).getBoard()));

		Node n = new Node(null, board);
		queue.add(n);
		explore(5, board, queue);

		((AmazonLogic) obser).getGUI().repaint();
	}

	private void explore(int depth, Board board, PriorityQueue<Node> queue) {
		Node n = queue.poll();

	}
}

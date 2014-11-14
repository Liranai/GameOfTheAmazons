package ai;

import java.awt.Point;
import java.util.Observable;
import java.util.PriorityQueue;

import logic.AmazonLogic;
import model.Board;
import model.Move;
import model.Queen;
import ai.search.AStarNode;
import ai.search.NodeComparator;

public class AStarAI extends ArtificialIntelligence {

	private Board board;
	private PriorityQueue<AStarNode> queue;

	public AStarAI(boolean color) {
		super(color);
		queue = new PriorityQueue<AStarNode>(10, new NodeComparator());
	}

	@Override
	public void update(Observable obser, Object obj) {
		this.board = ((AmazonLogic) obser).getBoard();

		Queen queen = board.getQueens().get(0);
		Move move = new Move(queen, new Point(3, 3), new Point(6, 3));

		AStarNode n = new AStarNode(move.clone(), board.clone());
		queue.add(n);
		explore(5, board, queue);

		if (move.validate(board))
			board.move(move);

		((AmazonLogic) obser).setCurrentTurn(!color);
		((AmazonLogic) obser).getGUI().repaint();
	}

	private void explore(int depth, Board board, PriorityQueue<AStarNode> queue) {
		AStarNode n = queue.poll();

		n.evaluate();
		System.out.println(n.getF());
	}
}

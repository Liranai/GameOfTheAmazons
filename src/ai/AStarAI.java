package ai;

import java.awt.Point;
import java.util.Observable;
import java.util.PriorityQueue;
import java.util.Vector;

import logic.AmazonLogic;
import model.Board;
import model.Move;
import model.Queen;
import ai.search.AStarNode;
import ai.search.AStarNode.Status;
import ai.search.NodeComparator;

public class AStarAI extends ArtificialIntelligence {

	private Board board;

	public AStarAI(boolean color) {
		super(color);

	}

	@Override
	public void update(Observable obser, Object obj) {
		this.board = ((AmazonLogic) obser).getBoard();

		PriorityQueue<AStarNode> queue = new PriorityQueue<AStarNode>(10, new NodeComparator());

		System.out.println("RUNNING ASTAR");

		AStarNode n = new AStarNode(null, board.clone());
		queue.add(n);

		for (int i = 0; i < 250; i++) {
			explore(queue, queue.poll());

		}

		System.out.println(queue.size());

		AStarNode node = queue.peek();
		Vector<Move> moves = new Vector<Move>();

		while (node.getParent().getMove() != null) {
			moves.add(node.getMove());
			node = node.getParent();
		}

		for (Queen queen : board.getQueens())
			System.out.println(queen.getPosition());

		while (moves.size() > 0) {
			Move move = moves.remove(moves.size() - 1);
			// TODO: fix cloning of Queen/Board so that moves are different.
			System.out.println(move.getQueen().getPosition() + " to " + move.getTarget());
			if (move.validate(board))
				board.move(move);
			((AmazonLogic) obser).getGUI().repaint();
			// try {
			// Thread.sleep(2000);
			// } catch (InterruptedException e) {
			// }
		}

		System.out.println(board.getQueens().size());

		// if (node.getMove().validate(board)) {
		// board.move(node.getMove());
		// }

		((AmazonLogic) obser).setCurrentTurn(!color);
		((AmazonLogic) obser).getGUI().repaint();
	}

	private AStarNode explore(PriorityQueue<AStarNode> queue, AStarNode node) {
		if (node.getStatus().equals(Status.closed))
			return null;
		node.setStatus(Status.closed);

		if (node.getBoard().isGameOver()) {
			return node;
		}

		for (Queen queen : node.getBoard().getQueens()) {
			if (queen.isColor() == color) {

				for (int i = 0; i < Board.DIRECTIONS.length; i++) {
					Point p = new Point(queen.getPosition().x, queen.getPosition().y);
					while (p.x >= 0 && p.y >= 0 && p.x < 10 && p.y < 10) {
						p.translate(Board.DIRECTIONS[i][0], Board.DIRECTIONS[i][1]);
						if (node.getBoard().isEmpty(p)) {
							// TODO: fix Arrow location
							Move move = new Move(queen, p, queen.getPosition());
							AStarNode n = new AStarNode(move.clone(), node.getBoard().clone());
							n.setParent(node);
							n.evaluate();
							// if (queue.contains(n)) {
							// Iterator<AStarNode> iter = queue.iterator();
							// while (iter.hasNext()) {
							// AStarNode n2 = iter.next();
							// if (n2.equals(n)) {
							// if (n2.getG() > n.getG()) {
							// queue.remove(n);
							// break;
							// }
							// }
							// }
							// }
							queue.add(n);
						} else {
							break;
						}
					}
				}
			}
		}
		return null;
	}
}

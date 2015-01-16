package ai;

import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Vector;

import model.Board;
import model.Move;
import model.Queen;
import ai.search.AStarNode;
import ai.search.NodeComparator;

public class AStarAI extends ArtificialIntelligence {

	public static final int ITERATIONS = 100;
	public static final int PRIMARY_NODES = 50;

	private long nodes = 0;

	public AStarAI(boolean color) {
		super(color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "AStar";
	}

	@Override
	public Move getMove(Board board) {
		long time = System.currentTimeMillis();
		nodes = 0;
		PriorityQueue<AStarNode> queue = new PriorityQueue<AStarNode>(10, new NodeComparator());

		// System.out.println("Running: AStar2.0");

		explore(board.clone(), queue, null);

		if (queue.size() == 0) {
			return null;
		}

		int i = 0;
		while (queue.size() > 2 && i < AStarTwoAI.ITERATIONS) {

			nodes += queue.size();
			Vector<AStarNode> list = new Vector<AStarNode>();
			if (queue.size() >= AStarAI.PRIMARY_NODES) {
				for (int j = 0; j < AStarAI.PRIMARY_NODES; j++) {
					AStarNode node = queue.poll();
					node.setCounterMove(getCounterMove(node));
					node.evaluate();
					list.add(node);
				}
			} else {
				while (queue.size() > 0) {
					AStarNode node = queue.poll();
					node.setCounterMove(getCounterMove(node));
					node.evaluate();
					list.add(node);
				}
			}

			queue = new PriorityQueue<AStarNode>(10, new NodeComparator());
			for (AStarNode node : list) {
				queue.add(node);
			}

			AStarNode tNode = queue.poll();
			// if (AStarTwoAI.ITERATIONS > 50) {
			// if (i % (AStarTwoAI.ITERATIONS / 50) == 0) {
			// System.out.print(".");
			// }
			// } else {
			// System.out.print(".");
			// }
			explore(tNode.getAugmentedBoard(), queue, tNode);
			i++;
		}

		AStarNode node = queue.peek();
		while (node.getParent() != null) {
			node = node.getParent();
		}

		// System.out.println("\n For: " + (color ? " White" : " Black") +
		// " nodes: " + queue.size() + " value: " + node.getF());
		// System.out.println(node.getMove().getQueen().getPosition() + " to " +
		// node.getMove().getTarget() + " targeting " +
		// node.getMove().getArrow());

		long dt = System.currentTimeMillis() - time;

		if (super.MIN_TIME == 0) {
			super.MIN_TIME = dt;
			super.MAX_TIME = dt;
			super.MIN_NODES = nodes;
			super.MAX_NODES = nodes;
		} else {
			if (dt < super.MIN_TIME) {
				super.MIN_TIME = dt;
			}
			if (dt > super.MAX_TIME) {
				super.MAX_TIME = dt;
			}
			if (nodes < super.MIN_NODES) {
				super.MIN_NODES = nodes;
			}
			if (nodes > super.MAX_NODES) {
				super.MAX_NODES = nodes;
			}
		}

		super.MOVES++;
		super.AVG_TIME += dt;
		super.AVG_NODES += nodes;

		return node.getMove();
	}

	public void explore(Board board, PriorityQueue<AStarNode> queue, AStarNode parent) {
		if (board.isGameOver()) {
			return;
		}

		for (Queen queen : board.getQueens()) {
			if (queen.isColor() == color) {

				for (int i = 0; i < Board.DIRECTIONS.length; i++) {
					Point p = new Point(queen.getPosition().x, queen.getPosition().y);
					while (p.x >= 0 && p.y >= 0 && p.x < 10 && p.y < 10) {
						p.translate(Board.DIRECTIONS[i][0], Board.DIRECTIONS[i][1]);
						if (board.isEmpty(p)) {

							for (int j = 0; j < Board.DIRECTIONS.length; j++) {
								Point arrow = new Point(p.x, p.y);
								while (arrow.x >= 0 && arrow.y >= 0 && arrow.x < 10 && arrow.y < 10) {
									arrow.translate(Board.DIRECTIONS[j][0], Board.DIRECTIONS[j][1]);
									if (board.isEmpty(arrow) || arrow.equals(queen.getPosition())) {
										Move move = new Move(queen, p, arrow);
										AStarNode node = new AStarNode(move.clone(), board.clone());
										node.setParent(parent);
										// node.setCounterMove(getCounterMove(node));
										node.evaluate();
										queue.add(node);
									} else {
										break;
									}
								}
							}
						} else {
							break;
						}
					}
				}
			}
		}
	}

	public AStarNode getCounterMove(AStarNode node) {
		PriorityQueue<AStarNode> queue = new PriorityQueue<AStarNode>(10, new NodeComparator());

		for (Queen queen : node.getAugmentedBoard().getQueens()) {
			if (queen.isColor() == !color) {

				for (int i = 0; i < Board.DIRECTIONS.length; i++) {
					Point p = new Point(queen.getPosition().x, queen.getPosition().y);
					while (p.x >= 0 && p.y >= 0 && p.x < 10 && p.y < 10) {
						p.translate(Board.DIRECTIONS[i][0], Board.DIRECTIONS[i][1]);
						if (node.getAugmentedBoard().isEmpty(p)) {

							for (int j = 0; j < Board.DIRECTIONS.length; j++) {
								Point arrow = new Point(p.x, p.y);
								while (arrow.x >= 0 && arrow.y >= 0 && arrow.x < 10 && arrow.y < 10) {
									arrow.translate(Board.DIRECTIONS[j][0], Board.DIRECTIONS[j][1]);
									if (node.getAugmentedBoard().isEmpty(arrow) || arrow.equals(queen.getPosition())) {
										Move move = new Move(queen, p, arrow);
										AStarNode newNode = new AStarNode(move.clone(), node.getAugmentedBoard().clone());
										newNode.evaluate();
										queue.add(newNode);
									} else {
										break;
									}
								}
							}
						} else {
							break;
						}
					}
				}
			}
		}

		nodes += queue.size();

		return queue.poll();
	}
}

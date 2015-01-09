package ai;

import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Vector;

import model.Board;
import model.Move;
import model.Pair;
import model.Queen;
import ai.search.AStarThreeNode;
import ai.search.NodeThreeComparator;

public class AStarThreeAI extends ArtificialIntelligence {

	public static final int ITERATIONS = 150;

	private Vector<Pair<Queen, Integer>>[][] datamap;

	public AStarThreeAI(boolean color) {
		super(color);
		// TODO Auto-generated constructor stub

		datamap = (Vector<Pair<Queen, Integer>>[][]) new Object[10][10];
	}

	@Override
	public Move getMove(Board board) {
		PriorityQueue<AStarThreeNode> queue = new PriorityQueue<AStarThreeNode>(10, new NodeThreeComparator());

		evaluateBoard(board);

		Queen queen = findQueen(board);
		System.out.println("AStar3.0  -  Targeting: " + queen.getPosition());

		Move move = findMove(board, queen);

		// explore(board.clone(), queue, null);
		//
		// if (queue.size() == 0) {
		// return null;
		// }
		//
		// int i = 0;
		// while (queue.size() > 2 && i < AStarThreeAI.ITERATIONS) {
		// AStarThreeNode tNode = queue.poll();
		// if (AStarThreeAI.ITERATIONS > 50) {
		// if (i % (AStarThreeAI.ITERATIONS / 50) == 0) {
		// System.out.print(".");
		// }
		// } else {
		// System.out.print(".");
		// }
		// explore(tNode.getAugmentedBoard(), queue, tNode);
		// i++;
		// }
		//
		// AStarThreeNode node = queue.peek();
		// while (node.getParent() != null) {
		// node = node.getParent();
		// }
		//
		// System.out.println("\n For: " + (color ? " White" : " Black") +
		// " nodes: " + queue.size() + " value: " + node.getF());
		// System.out.println(node.getMove().getQueen().getPosition() + " to " +
		// node.getMove().getTarget() + " targeting " +
		// node.getMove().getArrow());

		return node.getMove();
	}

	private void evaluateBoard(Board board) {
		for (int i = 0; i < board.getField().length; i++) {
			for (int j = 0; j < board.getField()[0].length; j++) {

			}
		}
	}

	public void explore(Board board, PriorityQueue<AStarThreeNode> queue, AStarThreeNode parent) {
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
										AStarThreeNode node = new AStarThreeNode(move.clone(), board.clone());
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

	public AStarThreeNode getCounterMove(AStarThreeNode node) {
		PriorityQueue<AStarThreeNode> queue = new PriorityQueue<AStarThreeNode>(10, new NodeThreeComparator());

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
										AStarThreeNode newNode = new AStarThreeNode(move.clone(), node.getAugmentedBoard().clone());
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

		return queue.poll();
	}
}

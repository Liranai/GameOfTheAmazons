package ai;

import java.awt.Point;
import java.util.Observable;
import java.util.PriorityQueue;

import logic.AmazonLogic;
import model.Board;
import model.Move;
import model.Queen;
import ai.search.AStarTwoNode;
import ai.search.NodeTwoComparator;

public class AStarTwoAI extends ArtificialIntelligence {

	public AStarTwoAI(boolean color) {
		super(color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Observable obser, Object obj) {
		if (((AmazonLogic) obser).isCurrentTurn() == color) {
			Board board = ((AmazonLogic) obser).getBoard();
			PriorityQueue<AStarTwoNode> queue = new PriorityQueue<AStarTwoNode>(10, new NodeTwoComparator());

			// System.out.println("Running: AStar2.0");

			explore(board.clone(), queue, null);
			// System.out.println(queue.size());

			if (queue.size() == 0) {
				return;
			}

			int i = 0;
			while (queue.size() > 2 && i < 15) {
				AStarTwoNode tNode = queue.poll();
				System.out.print(".");
				// System.out.println("Node: " + i + " " + queue.size());
				explore(tNode.getAugmentedBoard(), queue, tNode);
				i++;
			}

			AStarTwoNode node = queue.peek();
			while (node.getParent() != null) {
				node = node.getParent();
			}

			System.out.println("For: " + (color ? " White" : " Black") + " nodes: " + queue.size());
			System.out.println(node.getMove().getQueen().getPosition() + " to " + node.getMove().getTarget() + " targeting " + node.getMove().getArrow());

			if (node.getMove().validate(board)) {
				board.move(node.getMove());
			}

			((AmazonLogic) obser).getGUI().repaint();
			((AmazonLogic) obser).endTurn();

			// ((AmazonLogic) obser).setBoard(board);

			// ((AmazonLogic) obser).setCurrentTurn(!color);

		}
	}

	public void explore(Board board, PriorityQueue<AStarTwoNode> queue, AStarTwoNode parent) {
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
										AStarTwoNode node = new AStarTwoNode(move.clone(), board.clone());
										node.setParent(parent);
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

}

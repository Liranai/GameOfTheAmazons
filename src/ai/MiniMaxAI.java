package ai;

import java.awt.Point;
import java.util.Observable;

import logic.AmazonLogic;
import model.Board;
import model.Move;
import model.Queen;
import ai.searchtree.Node;

public class MiniMaxAI extends ArtificialIntelligence {

	// private Board board;

	public MiniMaxAI(boolean color) {
		super(color);
	}

	@Override
	public void update(Observable obser, Object obj) {
		// this.board = ((AmazonLogic) obser).getBoard();

		Node root = constructTree(1, ((AmazonLogic) obser).getBoard());
		System.out.println(root.getChildren().size());
		for (Node child : root.getChildren()) {
			if (child.getChildren().size() != 0)
				System.out.println(child.getChildren().size());
		}

	}

	private Node constructTree(int n, Board board) {
		Node root = new Node(null, board, null, !color);

		makeChildren(root);
		for (Node child : root.getChildren()) {
			makeChildren(child);
			for (Node grandChild : child.getChildren()) {
				makeChildren(grandChild);
			}
		}

		// exploreNode(root, n, 0);
		return root;
	}

	private void exploreNode(Node node, int end, int position) {
		if (position == end) {
			return;
		}
		for (Node child : node.getChildren()) {
			makeChildren(child);
			exploreNode(child, end, position + 1);
		}
	}

	private void makeChildren(Node node) {
		// Vector<Move> possibleMoves = new Vector<Move>();

		for (Queen queen : node.getBoard().getQueens()) {
			if (queen.isColor() != node.isColor()) {

				for (int i = 0; i < Board.DIRECTIONS.length; i++) {
					Point p = new Point(queen.getPosition().x, queen.getPosition().y);
					while (p.x >= 0 && p.y >= 0 && p.x < 10 && p.y < 10) {
						p.translate(Board.DIRECTIONS[i][0], Board.DIRECTIONS[i][1]);
						if (node.getBoard().isEmpty(p)) {

							for (int j = 0; j < Board.DIRECTIONS.length; j++) {
								Point arrow = new Point(p.x, p.y);
								while (arrow.x >= 0 && arrow.y >= 0 && arrow.x < 10 && arrow.y < 10) {
									arrow.translate(Board.DIRECTIONS[j][0], Board.DIRECTIONS[j][1]);
									if (node.getBoard().isEmpty(arrow)) {
										Move move = new Move(queen, p, arrow);
										if (move.validate(node.getBoard())) {
											Board clone = node.getBoard().clone();
											clone.move(move);
											Node child = new Node(node, clone, move, !node.isColor());
											node.addChild(child);
										}
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

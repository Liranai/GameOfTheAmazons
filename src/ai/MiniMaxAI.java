package ai;

import java.awt.Point;
import java.util.Observable;
import java.util.Random;

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

		Random rand = new Random(System.currentTimeMillis());

		makeChildren(root);
		for (int i = 0; i < 250; i++) {
			Node child = root.getChildren().get(rand.nextInt(root.getChildren().size()));
			makeChildren(child);
		}
		// for (Node child : root.getChildren()) {
		// makeChildren(child);
		// }

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
				for (int tr = 0; tr < node.getBoard().getField().length; tr++) {
					for (int tc = 0; tc < node.getBoard().getField()[0].length; tc++) {
						for (int ar = 0; ar < node.getBoard().getField().length; ar++) {
							for (int ac = 0; ac < node.getBoard().getField()[0].length; ac++) {
								Move move = new Move(queen.clone(), new Point(tr, tc), new Point(ar, ac));
								if (move.validate(node.getBoard())) {
									// possibleMoves.add(move);
									Board clone = node.getBoard().clone();
									clone.move(move);
									Node child = new Node(node, clone, move, !node.isColor());
									node.addChild(child);
								}
							}
						}
					}
				}
			}
		}
	}

}

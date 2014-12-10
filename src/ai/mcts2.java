package ai;

import java.awt.Point;
import java.util.Observable;
import java.util.Random;
import java.util.Vector;

import logic.AmazonLogic;
import model.Board;
import model.Move;
import model.Queen;

public class mcts2 extends ArtificialIntelligence {

	private Vector<MCTSNode> firstChildren;
	// private Board board;

	private static final int depth = 3;
	private static final int iterations = 25;

	public mcts2(boolean color) {
		super(color);
	}

	@Override
	public void update(Observable obser, Object obj) {
		if (((AmazonLogic) obser).isCurrentTurn() == color) {
			firstChildren = new Vector<MCTSNode>();
			Board board = ((AmazonLogic) obser).getBoard();

			Move move = getMove(board);

			//System.out.println("Q: " + move.getQueen().getPosition() + " T: " + move.getTarget() + " A: " + move.getArrow());
			
			if (move.validate(board)) {
				board.move(move.getQueen(), move.getTarget(), move.getArrow());
			}
			
			
			((AmazonLogic) obser).endTurn();
			((AmazonLogic) obser).getGUI().repaint();
			//((AmazonLogic) obser).endTurn();
			
		}
	}

	public Move getMove(Board board) {
		
		fillChildren(board);
		setValues();

		MCTSNode max = firstChildren.get(0);
		for (MCTSNode node : firstChildren) {
			if (max.getAverage() < node.getAverage()) {
				max = node;
				//System.out.println("Choose: " + firstChildren.indexOf(node));
				//System.out.println("New value: " + max.getAverage());
			}
		}
		
		//System.out.println(firstChildren.size());
		
		return max.getMove();
	}

	public void fillChildren(Board board) {
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

										Board augmentedBoard = board.clone();
										augmentedBoard.move(move.clone());

										MCTSNode node = new MCTSNode(augmentedBoard, move.clone());
										firstChildren.addElement(node);
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

	public void setValues() {

		System.out.println(firstChildren.size());
		for (int i = 0; i < firstChildren.size(); i++) {
			// System.out.println("child number " + i);
			if (firstChildren.size() > 50) {
				if (i % (firstChildren.size() / 50) == 0) {
					System.out.print(".");
				}
			} else {
				System.out.print(".");
			}
			for (int j = 0; j < iterations; j++) {
				// System.out.println("iteration number " + j);
				firstChildren.get(i).addToAverage(MCTSSearch(firstChildren.get(i), depth, !color));
			}
		}
		System.out.print("\n");
	}

	public double MCTSSearch(MCTSNode root, int g, boolean turn) {
		double result = 0.0;
		if (g != 0) {
			MCTSNode newNode = randomMove(root, turn);
			result = MCTSSearch(newNode, g - 1, !turn);
		} else {
			result = root.calculateValue(turn);
		}
		return result;
	}

	// TODO: fix; somehow shooting OUTSIDE of board.
	public MCTSNode randomMove(MCTSNode root, boolean turn) {
		Random rand = new Random(System.currentTimeMillis());
		Vector<Move> moves = new Vector<Move>();

		for (Queen queen : root.getBoard().getQueens()) {
			if (queen.isColor() == turn) {

				for (int i = 0; i < Board.DIRECTIONS.length; i++) {
					Point p = new Point(queen.getPosition().x, queen.getPosition().y);
					while (p.x >= 0 && p.y >= 0 && p.x < 10 && p.y < 10) {
						p.translate(Board.DIRECTIONS[i][0], Board.DIRECTIONS[i][1]);
						if (root.getBoard().isEmpty(p)) {

							for (int j = 0; j < Board.DIRECTIONS.length; j++) {
								Point arrow = new Point(p.x, p.y);
								while (arrow.x >= 0 && arrow.y >= 0 && arrow.x < 10 && arrow.y < 10) {
									arrow.translate(Board.DIRECTIONS[j][0], Board.DIRECTIONS[j][1]);
									if (root.getBoard().isEmpty(arrow) || arrow.equals(queen.getPosition())) {
										Move move = new Move(queen, p, arrow);
										moves.add(move.clone());
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

		Move move = moves.get(rand.nextInt(moves.size()));
		Board augmentedBoard = root.getBoard().clone();

		augmentedBoard.move(move.clone());
		return new MCTSNode(augmentedBoard, move.clone());
	}
}

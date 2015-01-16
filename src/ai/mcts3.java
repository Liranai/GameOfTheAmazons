package ai;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import model.Board;
import model.Move;
import model.Queen;
import ai.search.MCTSNode;

public class mcts3 extends ArtificialIntelligence {

	private Vector<MCTSNode> firstChildren;
	// private Board board;

	private static int depth = 1;
	private static int iterations = 10;

	public mcts3(boolean color) {
		super(color);
	}

	@Override
	public Move getMove(Board board) {
		firstChildren = new Vector<MCTSNode>();

		Move move = findMove(board);

		return move;
	}

	// @Override
	// public void update(Observable obser, Object obj) {
	// if (((AmazonLogic) obser).isCurrentTurn() == color) {
	// firstChildren = new Vector<MCTSNode>();

	//
	// // System.out.println("Q: " + move.getQueen().getPosition() + " T: "
	// // + move.getTarget() + " A: " + move.getArrow());
	//
	// if (move.validate(board)) {
	// board.move(move);
	// } else {
	// System.err.println("Did not move");
	// }
	//
	// ((AmazonLogic) obser).endTurn();
	// ((AmazonLogic) obser).getGUI().repaint();
	// // ((AmazonLogic) obser).endTurn();
	//
	// }
	// }

	public Move findMove(Board board) {
		// for (int i = 0; i < board.getQueens().size(); i++) {
		// System.out.println(board.getQueens().get(i).getPosition());
		// }
		fillChildren(board);
		int childrencounter = 0;
		childrencounter = childrencounter + firstChildren.size();
		if(firstChildren.size() < 1500){
			depth = 2;
			iterations = 25;
		}
		if (firstChildren.size() < 750) {
			depth = 4;
			iterations = 40;

		}
		if (firstChildren.size() < 375) {
			depth = 8;
			iterations = 60;
		}
		if (firstChildren.size() < 150) {
			depth = 12;
			iterations = 110;
		}

		if (firstChildren.size() == 1) {
			return firstChildren.get(0).getMove();
		} else {
			setValues();
			
			MCTSNode max = firstChildren.get(0);
			System.out.println("-----------------------------------------");
			for (MCTSNode node : firstChildren) {
				if (max.getAverage() < node.getAverage()) {
					max = node;
					System.out.println("Choose: " + firstChildren.indexOf(node));
					System.out.println("New value: " + max.getAverage());
					
				}
			}
			// for (int i = 0; i < max.getBoard().getQueens().size(); i++) {
			// System.out.println(max.getBoard().getQueens().get(i).getPosition());
			// }
			return max.getMove();
		}
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
		System.out.println("iterations = " + iterations);
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
				firstChildren.get(i).addToAverage(MCTSSearch(firstChildren.get(i), !color));
			}
		}
		System.out.print("\n");
	}

	public double MCTSSearch(MCTSNode root, boolean turn) {

			double result = 0.0;
			if (root.getBoard().isGameOver() != true) {
				MCTSNode newNode = randomMove(root, turn);
				result = MCTSSearch(newNode,  !turn);
			} else {
				//root.getBoard().printBoard();
				if(root.getBoard().getMobility(!color) == 0){
					result = 1000;
				}else{
					//root.getBoard().printBoard();
					result = 1;
				}
			}
			//System.out.println(result);
			return result;
	}

	// TODO: fix; somehow shooting OUTSIDE of board.
	public MCTSNode randomMove(MCTSNode root, boolean turn) {
		Random rand = new Random(System.currentTimeMillis());
		Vector<Move> moves = new Vector<Move>();
		if (root != null) {
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
		}
		try {
			Move move = moves.get(rand.nextInt(moves.size()));

			Board augmentedBoard = root.getBoard().clone();

			augmentedBoard.move(move.clone());
			return new MCTSNode(augmentedBoard, move.clone());
		} catch (IllegalArgumentException e) {
			System.out.println("the crash");
			// System.out.println(moves.size());
			return null;
		}
	}
}

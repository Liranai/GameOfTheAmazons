package ai;

import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Vector;

import model.Board;
import model.Move;
import model.Queen;
import ai.search.AStarNode;
import ai.search.GuidedMCTSNode;
import ai.search.NodeComparator;

public class GuidedMCTSAI extends ArtificialIntelligence {

	private Random rand;

	public static int BRANCHES = 250;
	public static int ITERATIONS = 500;
	public static int ASTAR_NODES = 250;
	public static int COUNTER_MOVES = 100;

	long nodes = 0;

	public GuidedMCTSAI(boolean colour) {
		super(colour);

		rand = new Random(System.currentTimeMillis());
	}

	public String getName() {
		return "Gwens Guided MCTS";
	}

	public Move getMove(Board board) {
		long time = System.currentTimeMillis();

		Move move = null;
		if (board.getArrows().size() < 35) {
			move = getAStarMove(board);
		} else {
			move = getMCTSMove(board);
		}

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

		System.out.println("Time: " + (System.currentTimeMillis() - time));

		return move;
	}

	private Move getAStarMove(Board board) {
		PriorityQueue<AStarNode> queue = new PriorityQueue<AStarNode>(10, new NodeComparator());

		explore(board.clone(), queue, null);

		if (queue.size() == 0) {
			return null;
		}

		int i = 0;
		while (queue.size() > 2 && i < ASTAR_NODES) {

			nodes += queue.size();
			Vector<AStarNode> list = new Vector<AStarNode>();
			if (queue.size() >= COUNTER_MOVES) {
				for (int j = 0; j < COUNTER_MOVES; j++) {
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

		return node.getMove();
	}

	private Move getMCTSMove(Board board) {
		Vector<GuidedMCTSNode> rootNodes = new Vector<GuidedMCTSNode>();

		for (int i = 0; i < BRANCHES; i++) {
			Move randMove = getRandomMove(board.clone(), super.color);
			GuidedMCTSNode node = new GuidedMCTSNode(board.clone(), randMove.clone());
			rootNodes.add(node);
		}

		for (int i = 0; i < rootNodes.size(); i++) {
			GuidedMCTSNode node = rootNodes.get(i);
			for (int j = 0; j < ITERATIONS; j++) {
				node.setValue(node.getValue() + findValue(node, super.color, 0));
			}
		}

		GuidedMCTSNode maxNode = null;
		for (GuidedMCTSNode node : rootNodes) {
			if (maxNode == null) {
				maxNode = node;
			} else {
				if (node.getValue() > maxNode.getValue()) {
					maxNode = node;
				}
			}
		}

		// System.out.println("Time: " + (System.currentTimeMillis() - time));
		// System.out.println(maxNode.getMove().getQueen().getPosition() +
		// " to " + maxNode.getMove().getTarget() + " targeting " +
		// maxNode.getMove().getArrow());
		return maxNode.getMove();
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

	private long findValue(GuidedMCTSNode node, boolean colour, long result) {
		if (node.getAugmentedBoard().isGameOver()) {
			if (node.getAugmentedBoard().getNumberOfMoves(!super.color) == 0) {
				return 10;
			} else {
				return -20;
			}
		} else {
			Board board = node.getAugmentedBoard();
			Move move = getRandomMove(board, !colour);
			GuidedMCTSNode newNode = new GuidedMCTSNode(board.clone(), move.clone());
			// GuidedMCTSNode newNode = new
			// GuidedMCTSNode(node.getAugmentedBoard().clone(),
			// getRandomMove(node.getAugmentedBoard().clone(), !colour));
			result += findValue(newNode, !colour, result);
		}
		return result;
	}

	private Move getRandomMove(Board board, boolean colour) {
		if (board.getArrows().size() >= 40) {
			return randomSecondaryMove(board, colour);
		} else {
			return randomPrimaryMove(board, colour);
		}
	}

	private Move randomPrimaryMove(Board board, boolean colour) {
		Move move = null;
		while (move == null) {
			Queen queen = board.getQueens().get(rand.nextInt(4) + (colour ? 4 : 0));
			Point target = new Point(queen.getPosition().x, queen.getPosition().y);
			target.translate(Board.DIRECTIONS[rand.nextInt(8)][0], Board.DIRECTIONS[rand.nextInt(8)][1]);
			if (board.isEmpty(target)) {
				Point arrow = new Point(target.x, target.y);
				arrow.translate(Board.DIRECTIONS[rand.nextInt(8)][0], Board.DIRECTIONS[rand.nextInt(8)][1]);
				if (board.isEmpty(arrow) || arrow.equals(queen.getPosition())) {
					move = new Move(queen.clone(), target, arrow);
					if (!move.validate(board)) {
						move = null;
					}
				} else {
					continue;
				}
			} else {
				continue;
			}
		}
		return move.clone();
	}

	private Move randomSecondaryMove(Board board, boolean colour) {
		Vector<Move> moves = new Vector<Move>();
		for (Queen queen : board.getQueens()) {
			if (queen.isColor() == colour) {

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
										Move move = new Move(queen.clone(), p, arrow);
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
		return move.clone();
	}
}

package ai.search;

import lombok.Getter;
import model.Board;
import model.Move;

@Getter
public class AStarNode {

	private double g, h, f;
	private Status status = Status.unexplored;
	private AStarNode parent;
	private Move move;
	private Board board;

	public AStarNode(Move move, Board board) {
		this.move = move;
		this.board = board;
	}

	public void evaluate() {
		AStarNode n = this;
		int i = 0;
		while (n != null) {
			i++;
			n = n.getParent();
		}
		g = (double) i;

		h = calculateH();

		f = g + h;
	}

	private double calculateH() {
		Board tempBoard = board.clone();
		tempBoard.move(move);

		return tempBoard.getMobility(move.getQueen().isColor());
	}

	@Override
	public boolean equals(Object obj) {
		return (((AStarNode) obj).getBoard().equals(board) && (((AStarNode) obj).getMove().equals(move)));
	}

	public enum Status {
		closed, open, unexplored;
	}
}
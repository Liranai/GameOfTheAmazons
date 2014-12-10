package ai.search;

import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.Move;

@Getter
public class AStarNode {

	private double g, h, f;
	@Setter
	private Status status = Status.unexplored;
	@Setter
	private AStarNode parent;
	private Move move;
	private Board board;

	public AStarNode(Move move, Board board) {
		this.move = move;
		this.board = board;
		if (move != null)
			board.move(move.clone());
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
		// Board tempBoard = board.clone();
		// tempBoard.move(move);

		// return tempBoard.getMobility(move.getQueen().isColor());
		if (move != null)
			return board.getMobility(move.getQueen().isColor());
		return 0.0;
	}

	@Override
	public boolean equals(Object obj) {
		return (((AStarNode) obj).getBoard().equals(board) && (((AStarNode) obj).getMove().equals(move)));
	}

	public enum Status {
		closed, open, unexplored;
	}
}
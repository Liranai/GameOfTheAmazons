package ai.search;

import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.Move;

@Getter
public class AStarTwoNode {

	private double g, h, f;
	@Setter
	private Status status = Status.unexplored;
	@Setter
	private AStarTwoNode parent;
	private Move move;
	private Board augmentedBoard, board;

	public AStarTwoNode(Move move, Board board) {
		this.board = board;
		this.move = move;
		augmentedBoard = board.clone();
		augmentedBoard.move(move.clone());
	}

	public void evaluate() {
		AStarTwoNode n = this;
		int i = 0;
		while (n != null) {
			i++;
			n = n.getParent();
		}
		g = (double) i;

		if (move != null)
			h = augmentedBoard.getMobility(move.getQueen().isColor());
		else
			h = 0.0;

		f = g + h;
	}

	@Override
	public boolean equals(Object obj) {
		return (((AStarTwoNode) obj).getBoard().equals(board) && (((AStarTwoNode) obj).getMove().equals(move)));
	}

	public enum Status {
		closed, open, unexplored;
	}
}

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
	@Setter
	private AStarNode counterMove;
	private Move move;
	private Board augmentedBoard, board;

	public AStarNode(Move move, Board board) {
		this.board = board;
		this.move = move;
		augmentedBoard = board.clone();
		augmentedBoard.move(move.clone());
	}

	public void evaluate() {
		AStarNode n = this;
		int i = 0;
		while (n != null) {
			i++;
			n = n.getParent();
		}
		g = (double) i;

		if (move != null)
			// h = MobilityTerritory.TM(augmentedBoard,
			// move.getQueen().isColor());
			h = augmentedBoard.getMobility(move.getQueen().isColor());
		else
			h = 0.0;

		f = g + h + (Math.random() / 1000.0);
		if (counterMove != null) {
			f = f + (counterMove.getF() / 2.0);
		}
	}

	@Override
	public boolean equals(Object obj) {
		return (((AStarNode) obj).getBoard().equals(board) && (((AStarNode) obj).getMove().equals(move)));
	}

	public enum Status {
		closed, open, unexplored;
	}
}

package ai.search;

import java.util.Vector;

import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.Move;

@Getter
public class GuidedMCTSNode {

	@Setter
	private double value, boardValue;
	private GuidedMCTSNode parent;
	private Board augmentedBoard;
	private Move move;

	private Vector<GuidedMCTSNode> children;

	public GuidedMCTSNode(Board board, Move move) {
		this.move = move.clone();
		Board tempBoard = board.clone();
		tempBoard.move(move.clone());
		// augmentedBoard = tempBoard.moveReturn(this.move);
		augmentedBoard = tempBoard;
	}

	public void evaluate(boolean colour) {
		boardValue = augmentedBoard.getMobility(colour);
	}

	@Override
	public boolean equals(Object obj) {
		return (((GuidedMCTSNode) obj).getAugmentedBoard().equals(augmentedBoard) && (((GuidedMCTSNode) obj).getMove().equals(move)));
	}

}

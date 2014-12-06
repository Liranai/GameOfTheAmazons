package ai;

import java.util.Vector;

import lombok.Getter;
import model.Board;
import model.Move;
import evaluation.Mobility;

@Getter
public class MCTSNode {
	private Board board;
	private Move move;
	private int times = 0;
	private MCTSNode parent;

	private Double average = 0.0;
	private Vector<MCTSNode> children = new Vector<MCTSNode>();

	public MCTSNode(Board board, Move move) {
		this.board = board;
		this.move = move;
	}

	public void addToAverage(double amount) {
		times++;
		if (average == 0.0) {
			average = amount;
		} else {
			average = (average * (times - 1) + amount) / times;
		}
	}

	int calculateValue(boolean color) {
		return Mobility.getMobilityFeature(color, board);
	}
}

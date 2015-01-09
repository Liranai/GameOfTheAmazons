package ai.search;

import java.util.Vector;

import lombok.Getter;
import model.Board;
import model.Move;

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

	public double calculateValue(boolean color) {
		// System.out.println(color);
		return board.getMobility(color);
		// return Mobility.getMobilityFeature(!color, board);
	}
}

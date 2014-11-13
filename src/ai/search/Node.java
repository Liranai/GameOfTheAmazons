package ai.search;

import lombok.Getter;
import model.Board;
import model.Move;

@Getter
public class Node {

	private double g, h, f;
	private Status status = Status.unexplored;
	private Node parent;
	private Move move;
	private Board board;

	public Node(Move move) {
		this.move = move;
	}

	@Override
	public boolean equals(Object obj) {
		return (((Node) obj).getBoard().equals(board) && (((Node) obj).getMove().equals(move)));
	}

	public enum Status {
		closed, open, unexplored;
	}
}

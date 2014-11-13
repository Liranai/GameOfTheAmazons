package ai.search;

import lombok.Getter;
import model.Board;
import model.Move;
import model.Queen;

@Getter
public class Node {

	private double g, h, f;
	private Status status = Status.unexplored;
	private Node parent;
	private Move move;
	private Board board;

	public Node(Move move, Board board) {
		this.move = move;
		this.board = board;
	}

	public void evaluate() {
		Node n = this;
		int i = 0;
		while (n != null) {
			i++;
			n = n.getParent();
		}
		g = (double) i;

		h = calculateH();

	}

	private double calculateH() {
		Board tempBoard = board.clone();
		tempBoard.move(move);

		int whiteMoves = 0;
		int blackMoves = 0;
		for (Queen queen : tempBoard.getQueens()) {

		}
	}

	@Override
	public boolean equals(Object obj) {
		return (((Node) obj).getBoard().equals(board) && (((Node) obj).getMove().equals(move)));
	}

	public enum Status {
		closed, open, unexplored;
	}
}
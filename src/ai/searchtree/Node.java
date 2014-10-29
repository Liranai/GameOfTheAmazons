package ai.searchtree;

import java.util.Vector;

import lombok.Getter;
import model.Board;
import model.Move;

@Getter
public class Node {

	private double value;
	private boolean color;
	private Move move;
	private Board board;
	private Node parent;
	private Vector<Node> children;

	public Node(Node parent, Board board, Move move, boolean color) {
		this.color = color;
		this.move = move;
		this.board = board;
		this.parent = parent;
		children = new Vector<Node>();
	}

	public void evaluate() {
		if (board.gameOver(color)) {
			value = 1.0;
		} else
			value = 0.0;
	}

	public void addChild(Node child) {
		children.add(child);
	}
}

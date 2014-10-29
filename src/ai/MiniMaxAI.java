package ai;

import java.util.Observable;

import logic.AmazonLogic;
import model.Board;
import ai.searchtree.Node;

public class MiniMaxAI extends ArtificialIntelligence {

	private Board board;

	public MiniMaxAI(boolean color) {
		super(color);
	}

	@Override
	public void update(Observable obser, Object obj) {
		this.board = ((AmazonLogic) obser).getBoard();

		Node root = constructTree(5);
	}

	private Node constructTree(int n) {
		Node root = new Node(null, null, null, false);

		return root;
	}

}

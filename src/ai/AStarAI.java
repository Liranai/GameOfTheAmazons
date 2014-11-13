package ai;

import java.util.Observable;

import logic.AmazonLogic;
import model.Board;

public class AStarAI extends ArtificialIntelligence {

	private Board board;

	public AStarAI(boolean color) {
		super(color);
	}

	@Override
	public void update(Observable obser, Object obj) {
		if (board != null) {
			System.out.println(board.equals(((AmazonLogic) obser).getBoard()));
		}
		this.board = ((AmazonLogic) obser).getBoard().clone();

		System.out.println(board.equals(((AmazonLogic) obser).getBoard()));

		((AmazonLogic) obser).getGUI().repaint();
	}

	// private static explore(Board board){
	// PriorityQueue<Node> queue = new PriorityQueue<Node, new
	// NodeComparator()>();
	// }
}

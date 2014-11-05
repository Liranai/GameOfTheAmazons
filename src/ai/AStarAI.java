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
		this.board = ((AmazonLogic) obser).getBoard();

		((AmazonLogic) obser).getGUI().repaint();
	}
}

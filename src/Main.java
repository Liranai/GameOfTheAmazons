import gui.InfoPanel;
import logic.AmazonLogic;
import ai.AStarAI;
import ai.ArtificialIntelligence;
import ai.RandomAI;
import ai.mcts2;

public class Main {

	// TODO fix recursive repainting bug that is created by running two AI's
	// against each other.
	public static void main(String[] args) {

		InfoPanel panel = new InfoPanel();
		ArtificialIntelligence selectedAI = new mcts2(false);
		ArtificialIntelligence selectedAI2 = new AStarAI(true);

		AmazonLogic logic = null;
		if (selectedAI2 != null)
			logic = new AmazonLogic(panel, selectedAI, selectedAI2);
		else if (selectedAI != null) {
			logic = new AmazonLogic(panel, selectedAI);
		} else {
			logic = new AmazonLogic(panel);
		}
		Thread t = new Thread(logic);
		t.start();
		// logic.addObserver(panel);

		// Board board = new Board();
		// AmazonUI ui = new AmazonUI(board);
	}
}

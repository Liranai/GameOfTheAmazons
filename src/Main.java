import gui.InfoPanel;
import logic.AmazonLogic;
import ai.ArtificialIntelligence;
import ai.mcts2;

public class Main {

	public static void main(String[] args) {
		InfoPanel panel = new InfoPanel();
		ArtificialIntelligence selectedAI = new mcts2(false);
		ArtificialIntelligence selectedAI2 = new mcts2(true);
		// ArtificialIntelligence selectedAI = null;

		AmazonLogic logic = new AmazonLogic(panel);
		if (selectedAI != null) {
			logic.addObserver(selectedAI);
			logic.addObserver(selectedAI2);
		}
		logic.addObserver(panel);

		// Board board = new Board();
		// AmazonUI ui = new AmazonUI(board);
	}
}

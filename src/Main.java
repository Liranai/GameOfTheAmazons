import gui.InfoPanel;
import logic.AmazonLogic;
import ai.AStarTwoAI;
import ai.ArtificialIntelligence;

public class Main {

	// TODO fix recursive repainting bug that is created by running two AI's
	// against each other.
	public static void main(String[] args) {

		InfoPanel panel = new InfoPanel();
		ArtificialIntelligence selectedAI = new AStarTwoAI(false);
		ArtificialIntelligence selectedAI2 = new AStarTwoAI(true);
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

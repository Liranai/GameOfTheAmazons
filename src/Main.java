import gui.InfoPanel;
import logic.AmazonLogic;
import ai.AStarThreeAI;
import ai.AStarTwoAI;
import ai.ArtificialIntelligence;

public class Main {

	// TODO fix recursive repainting bug that is created by running two AI's
	// against each other.
	public static void main(String[] args) {

		InfoPanel panel = new InfoPanel();
		ArtificialIntelligence selectedAI = new AStarTwoAI(false);
		ArtificialIntelligence selectedAI2 = new AStarThreeAI(true);

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

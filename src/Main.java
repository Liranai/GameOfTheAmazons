import gui.InfoPanel;
import logic.AmazonLogic;
import ai.ArtificialIntelligence;
import ai.MiniMaxAI;

public class Main {

	public static void main(String[] args) {
		InfoPanel panel = new InfoPanel();
		ArtificialIntelligence selectedAI = new MiniMaxAI(false);

		AmazonLogic logic;
		if (selectedAI != null)
			logic = new AmazonLogic(panel, selectedAI);
		else
			logic = new AmazonLogic(panel);
		logic.addObserver(panel);
		logic.addObserver(selectedAI);

		// Board board = new Board();
		// AmazonUI ui = new AmazonUI(board);
	}
}

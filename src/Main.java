import gui.TurnPanel;
import logic.AmazonLogic;

public class Main {

	public static void main(String[] args) {
		TurnPanel panel = new TurnPanel();

		AmazonLogic logic = new AmazonLogic(panel);
		logic.addObserver(panel);

		// Board board = new Board();
		// AmazonUI ui = new AmazonUI(board);
	}
}

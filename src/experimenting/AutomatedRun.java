package experimenting;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import logic.AmazonLogic;
import ai.AStarAI;
import ai.ArtificialIntelligence;
import ai.mcts2;

public class AutomatedRun {

	public static int GAME_NUMBER = 0;

	public static final int NUMBER_OF_EXPERIMENTS = 25;
	public static final String FILE_NAME = "MCTS2_vs_AStar_25";
	public static final int MCTS_DEPTH = 9;
	public static final int MCTS_ITERATIONS = 300;

	public static void main(String[] args) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_NAME + ".txt")));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// mcts2.DEPTH = MCTS_DEPTH;
		// mcts2.ITERATIONS = MCTS_ITERATIONS;

		for (int i = 0; i < NUMBER_OF_EXPERIMENTS; i++) {
			GAME_NUMBER = i;
			System.out.println("Running experiment " + (GAME_NUMBER + 1));

			ArtificialIntelligence AI1 = new mcts2(true);
			ArtificialIntelligence AI2 = new AStarAI(false);

			AmazonLogic logic = new AmazonLogic(AI1, AI2);
			Thread t = new Thread(logic);
			t.start();

			while (logic.isRunning()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println(" ============== EXPERIMENT DONE ============== ");
	}
}

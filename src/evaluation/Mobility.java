package evaluation;

import java.awt.Point;

import model.Board;
import model.Queen;

/*
 * Mobility = sum of the possible moves of all his/her queens
 * Mobility feature =  subtracting the opponent’s mobility from the 
 player’s
 */
public class Mobility {

	/*
	 * @return Mobility feature
	 */
	public static int getMobilityFeature(boolean colour, Board board) {
		int whiteMobility = getMobility(true, board);
		int blackMobility = getMobility(false, board);
		if (colour = true) {
			return whiteMobility - blackMobility;
		} else {
			return blackMobility - whiteMobility;
		}
	}

	/**
	 * Returns number of possible moves for color, including every different
	 * arrow location
	 * 
	 * @param colour
	 *            color of player in question
	 * @param board
	 *            board to use
	 * @return int mobility
	 */
	public static int getMobility(boolean colour, Board board) {
		int mobility = 0;

		for (Queen queen : Evaluation.getQueens(colour, board)) {
			for (Point move : Evaluation.getLocations(queen.getPosition(), board)) {
				mobility += Evaluation.getLocations(move, board).size();
			}
		}
		System.out.println("mob" + mobility);
		return mobility;
	}

}

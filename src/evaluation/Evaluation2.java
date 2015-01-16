package evaluation;

import java.awt.Point;

import model.Board;
import model.Queen;

public class Evaluation2 {

	public static int getMobilityFeature(boolean colour, Board board) {
		int whiteMoves = 0;
		int blackMoves = 0;

		for (Queen queen : board.getQueens()) {
			int movement = 0;

			for (int i = 0; i < Board.DIRECTIONS.length; i++) {
				Point p = new Point(queen.getPosition().x, queen.getPosition().y);
				while (p.x >= 0 && p.y >= 0 && p.x < 10 && p.y < 10) {
					p.translate(Board.DIRECTIONS[i][0], Board.DIRECTIONS[i][1]);
					if (board.isEmpty(p)) {
						movement++;
					} else {
						break;
					}
				}
			}
			if (queen.isColor()) {
				whiteMoves += movement;
			} else {
				blackMoves += movement;
			}
		}

		if (colour) {
			return blackMoves - whiteMoves;
		} else {
			return whiteMoves - blackMoves;
		}
	}

	public static int getMobilityTerritory(boolean colour, Board board) {
		return -1;
	}
}

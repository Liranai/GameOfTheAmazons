package ai.mapping;

import model.Board;

public class AStarDataMap {

	private Board board;
	private ComplexField[][] field;

	public AStarDataMap(Board board) {
		this.board = board;

		field = new ComplexField[board.getField().length][board.getField()[0].length];
		for (int i = 0; i < board.getField().length; i++) {
			for (int j = 0; j < board.getField()[0].length; j++) {
				field[i][j] = new ComplexField(i, j);
			}
		}
	}
}

package model;

import java.awt.Point;

import lombok.Getter;

@Getter
public class Move {

	private Queen queen;
	private Point target, arrow;

	public Move(Queen queen, Point target, Point arrow) {
		this.queen = queen;
		this.target = target;
		this.arrow = arrow;
	}

	public boolean validate(Board board) {
		if (target.equals(arrow)) {
			System.out.println("Queen shot herself");
			return false;
		}
		if (board.getField()[queen.getPosition().x][queen.getPosition().y] == GameObject.Empty) {
			System.out.println("No Queen found");
			return false;
		}
		if (!((target.x >= 0 && target.x < 10 && target.y >= 0 && target.y < 10) && ((target.x == queen.getPosition().x && target.y != queen.getPosition().y)
				|| (target.y == queen.getPosition().y && target.x != queen.getPosition().x) || (Math.abs(target.x - queen.getPosition().x) == Math.abs(target.y - queen.getPosition().y))))) {
			System.out.println("Queen moved in an unQueenlike manner");
			return false;
		}
		if (!((arrow.x >= 0 && arrow.x < 10 && arrow.y >= 0 && arrow.y < 10) && ((arrow.x == target.x && arrow.y != target.y) || (arrow.y == target.y && arrow.x != target.x) || (Math.abs(arrow.x
				- target.x) == Math.abs(arrow.y - target.y))))) {
			System.out.println("Arrow moved in an unQueenlike manner");
			return false;
		}
		if (!checkMove(board, queen.getPosition(), target)) {
			System.out.println("Queen went past something");
			return false;
		}
		if (!checkMove(board, target, arrow)) {
			System.out.println("Arrow went past something");
			return false;
		}
		return true;
	}

	private boolean checkMove(Board board, Point start, Point target) {
		double absx = Math.abs(target.x - start.x) + 0.001;
		double absy = Math.abs(target.y - start.y) + 0.001;

		int motionx = (int) Math.round((target.x - start.x) / absx);
		int motiony = (int) Math.round((target.y - start.y) / absy);

		for (int i = 1; i < Math.max(absx, absy); i++) {
			if (board.getField()[(int) (start.x + i * motionx)][(int) (start.y + i * motiony)] != GameObject.Empty) {
				System.out.println((char) ((start.x + i * motionx) + 97) + "" + (10 - (start.y + i * motiony)) + " was "
						+ board.getField()[(int) (start.x + i * motionx)][(int) (start.y + i * motiony)]);
				return false;
			}
		}

		return true;
	}
}

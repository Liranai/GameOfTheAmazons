package ai;

import java.awt.Point;
import java.util.Observable;
import java.util.Vector;

import logic.AmazonLogic;
import model.Board;
import model.Move;
import model.Queen;

public class RandomAI extends ArtificialIntelligence {

	private Vector<Queen> queens = new Vector<Queen>();
	private Vector<Point> moveLocations = new Vector<Point>();
	private Vector<Point> shootLocations = new Vector<Point>();
	private Board board;

	public RandomAI(boolean color) {
		super(color);
	}

	public Move getMove() {
		getQueens(color);
		Queen chosenQueen = fillMoveLocations();
		Point target = chooseMove();
		fillShootLocations(target, chosenQueen);
		Point shootTarget = shoot();
		return new Move(chosenQueen, target, shootTarget);
	}

	@Override
	public void update(Observable obser, Object obj) {
		queens = new Vector<Queen>();
		moveLocations = new Vector<Point>();
		shootLocations = new Vector<Point>();

		this.board = ((AmazonLogic) obser).getBoard();
		if (((AmazonLogic) obser).isCurrentTurn() == color) {
			Move move = getMove();
			move.validate(board);
			board.move(move);
			((AmazonLogic) obser).setCurrentTurn(!color);
		}
		((AmazonLogic) obser).getGUI().repaint();
	}

	private void getQueens(boolean color) {
		Vector<Queen> tempqueens = board.getQueens();
		for (int i = 0; i < tempqueens.size(); i++) {
			int cntr = 0;
			if (tempqueens.get(i).isColor() == color) {
				Queen queenToPlace = tempqueens.get(i);
				queens.add(cntr, queenToPlace);
				cntr++;
			}
		}
	}

	private Queen fillMoveLocations() {
		Queen chosenQueen = null;
		boolean moveMade = false;
		while (moveMade == false) {
			int q = (int) (Math.random() * queens.size());
			Queen queen = queens.get(q);
			boolean filled = false;
			while (filled == false) {
				Point nextPoint = new Point(0, 0);
				nextPoint.setLocation(queen.getPosition().getX(), queen.getPosition().getY() + 1.0);
				while (board.isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() + 1.0);
				}
				nextPoint.setLocation(queen.getPosition().getX() + 1.0, queen.getPosition().getY() + 1.0);
				while (board.isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() + 1.0, nextPoint.getY() + 1.0);
				}
				nextPoint.setLocation(queen.getPosition().getX() + 1, queen.getPosition().getY());
				while (board.isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY());
				}
				nextPoint.setLocation(queen.getPosition().getX() + 1, queen.getPosition().getY() - 1.0);
				while (board.isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY() - 1.0);
				}
				nextPoint.setLocation(queen.getPosition().getX(), queen.getPosition().getY() - 1.0);
				while (board.isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() - 1.0);
				}
				nextPoint.setLocation(queen.getPosition().getX() - 1, queen.getPosition().getY() - 1.0);
				while (board.isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() - 1.0);
				}
				nextPoint.setLocation(queen.getPosition().getX() - 1, queen.getPosition().getY());
				while (board.isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY());
				}
				nextPoint.setLocation(queen.getPosition().getX() - 1, queen.getPosition().getY() + 1.0);
				while (board.isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() + 1.0);
				}
				if (moveLocations.size() != 0) {
					filled = true;
				} else {
					queen = queens.get((int) (Math.random() * queens.size()));
				}
			}
			if (moveLocations.size() != 0) {
				moveMade = true;
				chosenQueen = queen;
			}
		}
		return chosenQueen;
	}

	private Point chooseMove() {
		Point target = moveLocations.get((int) (Math.random() * moveLocations.size()));
		return target;
	}

	private void fillShootLocations(Point move, Queen chosenQueen) {
		Point nextPoint = new Point(0, 0);
		nextPoint.setLocation(move.getX(), move.getY() + 1.0);
		while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
			Point aPoint = new Point();
			aPoint.setLocation(nextPoint);
			shootLocations.add(aPoint);
			nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() + 1.0);
		}
		nextPoint.setLocation(move.getX() + 1.0, move.getY() + 1.0);
		while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
			Point aPoint = new Point();
			aPoint.setLocation(nextPoint);
			shootLocations.add(aPoint);
			nextPoint.setLocation(nextPoint.getX() + 1.0, nextPoint.getY() + 1.0);
		}
		nextPoint.setLocation(move.getX() + 1, move.getY());
		while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
			Point aPoint = new Point();
			aPoint.setLocation(nextPoint);
			shootLocations.add(aPoint);
			nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY());
		}
		nextPoint.setLocation(move.getX() + 1, move.getY() - 1.0);
		while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
			Point aPoint = new Point();
			aPoint.setLocation(nextPoint);
			shootLocations.add(aPoint);
			nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY() - 1.0);
		}
		nextPoint.setLocation(move.getX(), move.getY() - 1.0);
		while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
			Point aPoint = new Point();
			aPoint.setLocation(nextPoint);
			shootLocations.add(aPoint);
			nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() - 1.0);
		}
		nextPoint.setLocation(move.getX() - 1, move.getY() - 1.0);
		while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
			Point aPoint = new Point();
			aPoint.setLocation(nextPoint);
			shootLocations.add(aPoint);
			nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() - 1.0);
		}
		nextPoint.setLocation(move.getX() - 1, move.getY());
		while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
			Point aPoint = new Point();
			aPoint.setLocation(nextPoint);
			shootLocations.add(aPoint);
			nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY());
		}
		nextPoint.setLocation(move.getX() - 1, move.getY() + 1.0);
		while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
			Point aPoint = new Point();
			aPoint.setLocation(nextPoint);
			shootLocations.add(aPoint);
			nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() + 1.0);
		}
	}

	private Point shoot() {
		Point target = shootLocations.get((int) (Math.random() * shootLocations.size()));
		System.out.println(moveLocations.size());
		System.out.println(shootLocations.size());
		return target;
	}

}

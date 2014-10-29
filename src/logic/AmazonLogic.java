package logic;

import gui.AmazonUI;
import gui.InfoPanel;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.GameObject;
import model.Move;
import model.Queen;
import ai.ArtificialIntelligence;

@Getter
public class AmazonLogic extends Observable implements MouseListener {

	private Board board;
	private AmazonUI GUI;
	private Queen selectedQueen;
	private Point target;
	@Setter
	private boolean currentTurn;
	private ArtificialIntelligence selectedAI;

	public static final int[][] Directions = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

	public AmazonLogic(InfoPanel panel, ArtificialIntelligence selectedAI) {
		this.selectedAI = selectedAI;
		constructBoard();

		GUI = new AmazonUI(board, panel, this);
	}

	public AmazonLogic(InfoPanel panel) {
		constructBoard();

		GUI = new AmazonUI(board, panel, this);
	}

	public void constructBoard() {
		board = new Board();
		board.addQueen(new Queen(new Point(0, 3), false));
		board.addQueen(new Queen(new Point(3, 0), false));
		board.addQueen(new Queen(new Point(6, 0), false));
		board.addQueen(new Queen(new Point(9, 3), false));

		board.addQueen(new Queen(new Point(0, 6), true));
		board.addQueen(new Queen(new Point(3, 9), true));
		board.addQueen(new Queen(new Point(6, 9), true));
		board.addQueen(new Queen(new Point(9, 6), true));

		currentTurn = true;
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		Point point = new Point((int) (Math.floor((me.getX() - (0.5 * AmazonUI.SQUARESIZE)) / AmazonUI.SQUARESIZE)), (int) (Math.floor(me.getY() / AmazonUI.SQUARESIZE)));

		if (selectedQueen == null) {
			if (board.hasQueen(point) != null && board.hasQueen(point).isColor() == currentTurn) {
				selectedQueen = board.hasQueen(point);
				target = null;
				board.setTarget(null);
				board.setHighlight(point);
				// currentTurn = !currentTurn;
			}
		} else if (selectedQueen != null && board.hasQueen(point) != null && selectedQueen.isColor() == currentTurn && board.hasQueen(point).isColor() == currentTurn
				&& selectedQueen != board.hasQueen(point)) {
			selectedQueen = board.hasQueen(point);
			target = null;
			board.setTarget(null);
			board.setHighlight(point);
		} else if (selectedQueen != null && target != null && target.equals(point) && selectedQueen.isColor() == currentTurn) {
			target = null;
			board.setTarget(null);
			// selectedQueen = board.hasQueen(point);
			// target = null;
			// board.setTarget(null);
			// board.setHighlight(point);
		} else if (selectedQueen != null && target != null && point.equals(selectedQueen.getPosition())) {
			Move move = new Move(selectedQueen, target, point);
			if (move.validate(board)) {
				board.move(selectedQueen, target, point);
				selectedQueen = null;
				target = null;
				board.setHighlight(null);
				board.setTarget(null);
				currentTurn = !currentTurn;
				GUI.repaint();
				if (checkMoves()) {
					System.out.println("Game over!");
				}
				setChanged();
				notifyObservers();
			}
		} else {
			if (board.isEmpty(point)) {
				if (target != null) {
					Move move = new Move(selectedQueen, target, point);
					// System.out.println("Q: " + selectedQueen.getPosition().x
					// + "," + selectedQueen.getPosition().y + " to: " +
					// target.x + "," + target.y + " shoot: " + point.x + "," +
					// point.y);
					if (move.validate(board)) {
						board.move(selectedQueen, target, point);
						selectedQueen = null;
						target = null;
						board.setHighlight(null);
						board.setTarget(null);
						currentTurn = !currentTurn;
						GUI.repaint();
						if (checkMoves()) {
							System.out.println("Game over!");
						}
						setChanged();
						notifyObservers();
					}
				} else {
					target = point;
					board.setTarget(point);
				}
			}
		}
		GUI.repaint();
	}

	public boolean checkMoves() {
		boolean whiteDead = true, blackDead = true;

		for (Queen queen : board.getQueens()) {
			for (int i = 0; i < 8; i++) {

				int x = queen.getPosition().x + Directions[i][0];
				int y = queen.getPosition().y + Directions[i][1];

				if (x < 0 || y < 0 || x >= 10 || y >= 10)
					continue;
				if (board.getField()[x][y] == GameObject.Empty)
					if (queen.isColor())
						whiteDead = false;
					else
						blackDead = false;
			}
		}

		if (whiteDead || blackDead) {
			if (currentTurn && blackDead) {
				System.out.println("White won!");
				// Black lost
			} else if (currentTurn && whiteDead) {
				System.out.println("Black won!");
				// White lost
			} else if (!currentTurn && whiteDead) {
				System.out.println("Black won!");
				// White lost
			} else if (!currentTurn && blackDead) {
				System.out.println("White won!");
				// Black lost
			}
			return true;
		}
		return false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}

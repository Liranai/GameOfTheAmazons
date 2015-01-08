package logic;

import gui.AmazonUI;
import gui.InfoPanel;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.GameObject;
import model.Move;
import model.Queen;
import ai.ArtificialIntelligence;

@Getter
public class AmazonLogic implements MouseListener, Runnable {

	private Board board;
	private boolean running, moved;
	private AmazonUI GUI;
	private Queen selectedQueen;
	private Point target;
	@Setter
	private boolean currentTurn;
	private Vector<ArtificialIntelligence> AIs;

	private int[][] Directions = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

	public AmazonLogic(InfoPanel panel, ArtificialIntelligence... selectedAIs) {
		AIs = new Vector<ArtificialIntelligence>();
		for (ArtificialIntelligence AI : selectedAIs)
			AIs.add(AI);
		constructBoard();

		running = true;
		moved = true;
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

	public void run() {
		while (running) {
			if (moved) {
				moved = false;
				for (ArtificialIntelligence AI : AIs) {
					if (AI.isColor() == currentTurn) {
						Move move = AI.getMove(board.clone());
						if (move != null) {
							endTurn(move);
						}
					}
				}
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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
			endTurn(move);
			selectedQueen = null;
			target = null;
			board.setHighlight(null);
			board.setTarget(null);
			// if (move.validate(board)) {
			// board.move(selectedQueen, target, point);
			// selectedQueen = null;
			// target = null;
			// board.setHighlight(null);
			// board.setTarget(null);
			// if (checkMoves()) {
			// System.out.println("Game over!");
			// }
			// }
		} else {
			if (board.isEmpty(point)) {
				if (target != null) {
					Move move = new Move(selectedQueen, target, point);
					// System.out.println("Q: " + selectedQueen.getPosition().x
					// + "," + selectedQueen.getPosition().y + " to: " +
					// target.x + "," + target.y + " shoot: " + point.x + "," +
					// point.y);
					endTurn(move);
					selectedQueen = null;
					target = null;
					board.setHighlight(null);
					board.setTarget(null);
					// if (move.validate(board)) {
					// board.move(selectedQueen, target, point);
					// selectedQueen = null;
					// target = null;
					// board.setHighlight(null);
					// board.setTarget(null);
					// if (checkMoves()) {
					// System.out.println("Game over!");
					// }
					// }
				} else {
					target = point;
					board.setTarget(point);
				}
			}
		}
		GUI.repaint();

	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void endTurn(Move move) {
		if (move.validate(board)) {
			board.move(move);
			currentTurn = !currentTurn;
			// board.printBoard();
			moved = true;
		}
		GUI.validate();
		GUI.repaint();

		// System.out.println("Board repainted");
		if (checkMoves()) {
			running = false;
			System.out.println("Game over!");
		}
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

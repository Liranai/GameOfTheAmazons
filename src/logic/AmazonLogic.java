package logic;

import gui.AmazonUI;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Board;
import model.Move;
import model.Queen;

public class AmazonLogic implements MouseListener {

	private Board board;
	private AmazonUI GUI;
	private Queen selectedQueen;
	private Point target;

	public AmazonLogic() {
		board = new Board();
		board.addQueen(new Queen(new Point(0, 3), false));
		board.addQueen(new Queen(new Point(3, 0), false));
		board.addQueen(new Queen(new Point(6, 0), false));
		board.addQueen(new Queen(new Point(9, 3), false));

		board.addQueen(new Queen(new Point(0, 6), true));
		board.addQueen(new Queen(new Point(3, 9), true));
		board.addQueen(new Queen(new Point(6, 9), true));
		board.addQueen(new Queen(new Point(9, 6), true));

		GUI = new AmazonUI(board, this);
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		Point point = new Point((int) (Math.floor((me.getX() - (0.5 * AmazonUI.SQUARESIZE)) / AmazonUI.SQUARESIZE)), (int) (Math.floor(me.getY() / AmazonUI.SQUARESIZE)));

		if (selectedQueen == null) {
			if (board.hasQueen(point) != null) {
				selectedQueen = board.hasQueen(point);
				target = null;
				board.setTarget(null);
				board.setHighlight(point);
			}
		} else if (selectedQueen != null && board.hasQueen(point) != null) {
			selectedQueen = board.hasQueen(point);
			target = null;
			board.setTarget(null);
			board.setHighlight(point);
		} else {
			if (board.isEmpty(point)) {
				if (target != null) {
					Move move = new Move(selectedQueen, target, point);
					if (move.validate(board)) {
						board.move(selectedQueen, target, point);
						selectedQueen = null;
						target = null;
						board.setHighlight(null);
						board.setTarget(null);
					}
				} else {
					target = point;
					board.setTarget(point);
				}
			}
		}
		GUI.repaint();
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

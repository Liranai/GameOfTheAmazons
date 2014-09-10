package logic;

import gui.AmazonUI;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Board;
import model.Queen;

public class AmazonLogic implements MouseListener {

	private Board board;
	private AmazonUI GUI;
	private Queen selectedQueen;

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
		selectedQueen = board.hasQueen(new Point((int) (Math.floor((me.getX() - (0.5 * AmazonUI.SQUARESIZE)) / AmazonUI.SQUARESIZE)), (int) (Math.floor(me.getY() / AmazonUI.SQUARESIZE))));

		if (selectedQueen != null) {
			board.setHighlight(selectedQueen.getPosition());
		} else {
			board.setHighlight(null);
		}
		GUI.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

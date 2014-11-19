package evaluation;
import model.*;

import java.awt.Point;
import java.util.*;

public class Evaluation {
   	private Vector<Point> moveLocations = new Vector<Point>();
   	private Vector<Point> kingMoveLocations = new Vector<Point>();
	private Board board;
	private Vector<Queen> queens = new Vector<Queen>();

	/*
	 * fills the queens vector with the locations of all queens of one colour
	 */
	public static Vector<Queen> getQueens(boolean colour, Board board) {
		Vector<Queen> tempqueens = board.getQueens();
		Vector<Queen> queens = new Vector<Queen>();
		for (int i = 0; i < tempqueens.size(); i++) {
			int cntr = 0;
			if (tempqueens.get(i).isColor() == colour) {
				Queen queenToPlace = tempqueens.get(i);
				queens.add(cntr, queenToPlace);
				cntr++;
			}
		}
		return queens;
	}
	
	/*
	 * Milan's code to find all Locations modified to return locations
	 */
	public static Vector<Point> getLocations(Point p, Board board){
		Vector<Point> locations = new Vector<Point>();
		boolean filled = false;
		while (!filled) {
			Point nextPoint = new Point(0, 0);
			nextPoint.setLocation(p.getX(), p.getY() + 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				locations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() + 1.0);
			}
			nextPoint.setLocation(p.getX() + 1.0, p.getY() + 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				locations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() + 1.0, nextPoint.getY() + 1.0);
			}
			nextPoint.setLocation(p.getX() + 1, p.getY());
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				locations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY());
			}
			nextPoint.setLocation(p.getX() + 1, p.getY() - 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				locations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY() - 1.0);
			}
			nextPoint.setLocation(p.getX(), p.getY() - 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				locations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() - 1.0);
			}
			nextPoint.setLocation(p.getX() - 1, p.getY() - 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				locations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() - 1.0);
			}
			nextPoint.setLocation(p.getX() - 1, p.getY());
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				locations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY());
			}
			nextPoint.setLocation(p.getX() - 1, p.getY() + 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				locations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() + 1.0);
			}
			filled = true;
		}
		return locations;
	}
	
	public static Vector<Point> getEmptySquares(Board board){
		Vector<Point> points = new Vector<Point>();
		for(int i=0; i<10;i++){
			for(int j= 0; j<10;j++){
				if(board.isEmpty(new Point(i,j))){
					points.add(new Point(i,j));
				}
			}
		}
		return points;
	}
	
	public static Vector<Point> getAllAvailablePoints(Board board){
		Vector<Point> points = new Vector<Point>();
		for(int i=0; i<10;i++){
			for(int j= 0; j<10;j++){
				points.add(new Point(i,j));
			}
		}
		return points;
	}
}

package evaluation;

import java.awt.Point;
import java.util.Vector;

import model.Board;
import model.Queen;

/*
 * Mobility = sum of the possible moves of all his/her queens
 * Mobility feature =  subtracting the opponent’s mobility from the 
player’s
 */
public class Mobility {
	private static int mobility;
	
	/*
	 * @return Mobility feature
	 */
	public static int getMobilityFeature(boolean colour, Board board){
		int whiteMobility = getMobility(true,board);
		mobility = 0;
		int blackMobility = getMobility(false,board);
		if(colour = true){
			return whiteMobility - blackMobility;
		}
		else{
			return blackMobility - whiteMobility;
		}
	}
	
	/*
	 * sums up the possible moves of all queens of one colour
	 */
	public static int getMobility(boolean colour, Board board){
		mobility = 0;
		Vector<Queen> queens = getQueens(colour, board);
		for(Queen q : queens){
			Point p = q.getPosition();
			Vector<Point> moveLocations = getLocations(p,board);
			for(Point o : moveLocations){
				Vector<Point> arrowLocations = getLocations(o,board);
				mobility += arrowLocations.size();
			}
		}
		System.out.println("mob" + mobility);
		return mobility;
	}
	
	/*
	 * fills the queens vector with the locations of all queens of one colour
	 */
	private static Vector<Queen> getQueens(boolean colour, Board board) {
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
	private static Vector<Point> getLocations(Point p, Board board){
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
			if (locations.size() != 0) {
				filled = true;
			}
		}
		return locations;
	}
	
}

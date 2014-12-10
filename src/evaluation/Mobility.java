package evaluation;

import java.awt.Point;
import java.util.Vector;

import evaluation.Evaluation;
import model.Board;
import model.Queen;

/*
 * Mobility = sum of the possible moves of all his/her queens
 * Mobility feature =  subtracting the opponent’s mobility from the 
player’s
 */
public class Mobility {
	
	/*
	 * @return Mobility feature
	 */
	public static int getMobilityFeature(boolean colour, Board board){
		int whiteMobility = getMobility(true,board);
		int blackMobility = getMobility(false,board);
		if(colour){
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
		int mobility = 0;
		Vector<Queen> queens = Evaluation.getQueens(colour, board);
		for(Queen q : queens){
			Point p = q.getPosition();
			Vector<Point> moveLocations = Evaluation.getLocations(p,board);
			for(Point o : moveLocations){
				Vector<Point> arrowLocations = Evaluation.getLocations(o,board);
				mobility += arrowLocations.size();
			}
		}
		return mobility;
	}
	
}

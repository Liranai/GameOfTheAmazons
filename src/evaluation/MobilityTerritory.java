package evaluation;

import java.awt.Point;
import java.util.Vector;

import model.Board;
import evaluation.Evaluation;

public class MobilityTerritory {
	
	/*
	 * gets Mobility & Territory evaluation value
	 */
	public static int TM(Board board, boolean currentTurn) { 
		int weight = 4;
		int blackPoints = 0; 
		int whitePoints = 0;
		int[][] blackEval = Evaluation.getQueenDistances(board,false);
		int[][] whiteEval = Evaluation.getQueenDistances(board,true);
		Vector<Point> points = Evaluation.getEmptySquares(board);
		for(Point p : points){ 
			int blackStonePly = blackEval[p.x][p.y];
			int whiteStonePly = whiteEval[p.x][p.y];
			int[][] blackMob = Evaluation.getMob(board, false);
			int[][] whiteMob = Evaluation.getMob(board, true);
			if (blackStonePly < whiteStonePly) 
				blackPoints = blackPoints + weight + blackMob[p.x][p.y]; 
			else if(whiteStonePly < blackStonePly)
				whitePoints = whitePoints + weight + whiteMob[p.x][p.y]; 
	}
		if(currentTurn = true)
			return (whitePoints - blackPoints); 
		else 
			return (blackPoints - whitePoints); 
	}
}

package evaluation;

import java.awt.Point;
import java.util.Vector;

import model.Board;
import evaluation.Evaluation;

public class MobilityTerritory {
	/*public static int TM(Board board, boolean currentTurn) { 
		int weight = 4;
		int blackPoints = 0; 
		int whitePoints = 0;
		int evenSquares = 0;
		int[][] blackEval = getQueenDistances(board,false);
		int[][] whiteEval = getQueenDistances(board,true);
		Vector<Point> points = getEmptySquares(board);
		for(Point p : points){ 
			int blackStonePly = blackEval[p.x][p.y];
			int whiteStonePly = whiteEval[p.x][p.y];
			//int blackMob = the number of black queens which can arrive at square x in one move. 
			//int whiteMob = the number of white queens which can arrive at square x in one move. 
			if (blackStonePly < whiteStonePly) 
				blackPoints = blackPoints + weight + blackMob; 
			else 
				whitePoints = whitePoints + weight + whiteMob; 
	}
		if(currentTurn = true)
			return (whitePoints - blackPoints); 
		else 
			return (blackPoints - whitePoints); 
	}*/
}

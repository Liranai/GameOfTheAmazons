package evaluation;

import java.awt.Point;
import java.util.Vector;

import model.Board;
import evaluation.Evaluation;

public class Territory {
	
	/*
	 * gets territory evaluation value
	 */
	public static int MSP(Board board, boolean currentTurn){
		int blackSquares = 0;
		int whiteSquares = 0;
		int evenSquares = 0;
		int[][] blackEval = Evaluation.getQueenDistances(board,false);
		int[][] whiteEval = Evaluation.getQueenDistances(board,true);
		Vector<Point> points = Evaluation.getEmptySquares(board);
		for(Point p : points){
			int blackStonePly = blackEval[p.x][p.y];
			int whiteStonePly = whiteEval[p.x][p.y];
			if (blackStonePly < whiteStonePly) 
				blackSquares++; 
			else if(blackStonePly > whiteStonePly)
				whiteSquares++;
			else evenSquares++;
		}
		System.out.println(whiteSquares);
		System.out.println(evenSquares);
		System.out.println(blackSquares);
		if(currentTurn)
			return (whiteSquares - blackSquares); 
		else 
			return (blackSquares - whiteSquares); 
	}
}



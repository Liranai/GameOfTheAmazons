package evaluation;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import model.Board;
import model.Queen;
import evaluation.Evaluation;

public class Territory {
	private static final int INFINITY = Integer.MAX_VALUE;

	/*
	 * shortest distance from 1 queen to 1 point
	 */
	public static int BFS(Board board, Point root, Point goal){
		Queue<Point> queue = new LinkedList<Point>();
		Vector<Point> v = new Vector<Point>();
		v.add(root);
		queue.add(root);
		while(!queue.isEmpty()){
			Point tmp = queue.remove();
			if(tmp.equals(goal)){
				return v.size();
			}
			Vector<Point> locations = Evaluation.getLocations(tmp,board);
			for(Point e : locations){
				Point u = e;
				if(!v.contains(u)){
					v.add(u);
					queue.add(u);
				}
			}
		}
		return INFINITY;
	}
	
	public static int[][] getQueenDistances(Board board,boolean colour){
		int[][] eval = new int[10][10];
		int[] possibleShortestDistances = new int[4]; //shortest distance for each queen to one point
		Vector<Point> points = Evaluation.getEmptySquares(board);
		Vector<Queen> queens = Evaluation.getQueens(colour, board);
		//for each queen of 1 colour, go through all points 
		//with no gameobject and get the shortest distance
		//of each queen to each point
		//points that cannot be reached get value INFINITY
		for(Queen queen : queens){
			for(Point p : points){
				for(int i = 0; i<4;i++){
					//Vector<Point> moveLocations = getLocations(queen.getPosition(),board);
					possibleShortestDistances[i] = BFS(board,queen.getPosition(),p);
				}
				int min = INFINITY;
				for (int i=0; i<possibleShortestDistances.length; i++){
					   if (possibleShortestDistances[i] < min){
					      min = possibleShortestDistances[i];
					   }
					}
				eval[p.x][p.y] = min;
			}
		}
		return eval;
	}
	
	public static int MSP(Board board, boolean currentTurn){
		int blackSquares = 0;
		int whiteSquares = 0;
		int evenSquares = 0;
		int[][] blackEval = getQueenDistances(board,false);
		int[][] whiteEval = getQueenDistances(board,true);
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
		if(currentTurn = true)
			return (whiteSquares - blackSquares); 
		else 
			return (blackSquares - whiteSquares); 
	}
	



}































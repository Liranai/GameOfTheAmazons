package evaluation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import model.Board;
import model.Queen;
import evaluation.Evaluation;

public class Territory {
	private static final int INFINITY = Integer.MAX_VALUE;

	public static int[][] BFS(Board board, Point root){
		Queue<Point> queue = new LinkedList<Point>();
		ArrayList<Point> v = new ArrayList<Point>();
		int[][] distances = new int[10][10];
		Point[][] previous = new Point[10][10];

		Vector<Point> allSquares = Evaluation.getAllAvailablePoints(board);
		for(Point o : allSquares){
			distances[o.x][o.y] = INFINITY;
			previous[o.x][o.y]= null; 
		}
		
		v.add(root);
		queue.add(root);
		distances[root.x][root.y] = 0;
		previous[root.x][root.y]= root; 
		Vector<Point> locations = Evaluation.getLocations(root,board);
		for(Point p : locations){
			distances[p.x][p.y] = 1;
			previous[p.x][p.y]= root; 
			v.add(p);
			queue.add(p);
		}
		while(!queue.isEmpty()){
			Point tmp = queue.remove();
			Vector<Point> tmpLocations = Evaluation.getLocations(tmp,board);
			for(Point u : tmpLocations){
				if(!v.contains(u)){
					if(distances[u.x][u.y] > distances[tmp.x][tmp.y] +1){
						distances[u.x][u.y]= distances[tmp.x][tmp.y] +1;
						previous[u.x][u.y] = tmp;
					}
					v.add(u);
					queue.add(u);
				}
			}
		}
		return distances;
	}
	
	public static int[][] getQueenDistances(Board board,boolean colour){
		int[][] eval = new int[10][10];
		Vector<Point> allSquares = Evaluation.getAllAvailablePoints(board);
		for(Point o : allSquares){
			eval[o.x][o.y] = INFINITY;
		}
		Vector<Queen> queens = Evaluation.getQueens(colour, board);
		for(Queen queen : queens){
			for(int j =0; j<10;j++){
				for(int k =0; k<10;k++){
					int[][] tmp = BFS(board,queen.getPosition());
					if(eval[j][k] > tmp[j][k]){
						eval[j][k] = tmp[j][k];
					}
				}
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































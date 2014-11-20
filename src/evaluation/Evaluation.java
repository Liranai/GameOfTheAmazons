package evaluation;
import model.*;

import java.awt.Point;
import java.util.*;

public class Evaluation {
	
	private static final int INFINITY = Integer.MAX_VALUE;
	
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
	
	/*
	 * @return a vector with all empty cells
	 */
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
	
	/*
	 * @return a vector with all points in the 10*10 board
	 */
	public static Vector<Point> getAllAvailablePoints(Board board){
		Vector<Point> points = new Vector<Point>();
		for(int i=0; i<10;i++){
			for(int j= 0; j<10;j++){
				points.add(new Point(i,j));
			}
		}
		return points;
	}
	
	/*
	 * goes through board and gets queen distances to all other points
	 */
	public static int[][] BFS(Board board, Point root){
		Queue<Point> queue = new LinkedList<Point>();
		ArrayList<Point> v = new ArrayList<Point>();
		int[][] distances = new int[10][10];
		Point[][] previous = new Point[10][10];

		Vector<Point> allSquares = getAllAvailablePoints(board);
		for(Point o : allSquares){
			distances[o.x][o.y] = INFINITY;
			previous[o.x][o.y]= null; 
		}
		
		v.add(root);
		queue.add(root);
		distances[root.x][root.y] = 0;
		previous[root.x][root.y]= root; 
		Vector<Point> locations = getLocations(root,board);
		for(Point p : locations){
			distances[p.x][p.y] = 1;
			previous[p.x][p.y]= root; 
			v.add(p);
			queue.add(p);
		}
		while(!queue.isEmpty()){
			Point tmp = queue.remove();
			Vector<Point> tmpLocations = getLocations(tmp,board);
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
	
	/*
	 * compares queen distances and returns a vector with the shortest distances of one player to each cell in the board
	 */
	public static int[][] getQueenDistances(Board board,boolean colour){
		int[][] eval = new int[10][10];
		Vector<Point> allSquares = getAllAvailablePoints(board);
		for(Point o : allSquares){
			eval[o.x][o.y] = INFINITY;
		}
		int[][]mob = new int[10][10];
		Vector<Queen> queens = getQueens(colour, board);
		for(Queen queen : queens){
			for(int j =0; j<10;j++){
				for(int k =0; k<10;k++){
					int[][] tmp = BFS(board,queen.getPosition());
					if(eval[j][k]==1)
						mob[j][k]++;
					if(eval[j][k] > tmp[j][k]){
						eval[j][k] = tmp[j][k];
					}
				}
			}
		}
		return eval;
	}
	
	public static int[][] calcMob(Board board, Point root){
		int[][] mob = new int[10][10];
		Vector<Point> locations = getLocations(root,board);
		for(Point p : locations){
			mob[p.x][p.y] = 1;
		}
		return mob;
	}
	
	/*
	 * Mob = the number of queens which can arrive at square x in one move. 
	 */
	public static int[][] getMob(Board board, boolean colour){
		int[][]mob = new int[10][10];
		Vector<Queen> queens = getQueens(colour, board);
		for(Queen queen : queens){
			int[][]tmp = calcMob(board,queen.getPosition());
			for(int j =0; j<10;j++){
				for(int k =0; k<10;k++){
					if(tmp[j][k] == 1)
						mob[j][k]++;
				}
			}
		}
		return mob;
	}
}

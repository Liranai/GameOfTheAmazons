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
			if (locations.size() != 0) {
				filled = true;
			}
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
	
	/*public ArrayList<Point> allAvailablePoints(Board board){
		ArrayList<Point> points = new ArrayList<Point>();
		for(int i=0; i<9;i++){
			for(int j= 0; j<9;j++){
				points.add(new Point(i,j));
			}
		}
		return points;
	}
	//currently just a general BFS algorithm
	//only visits all possible locations using queen moves => does not create path yet
	public void getQueenDistances(Node root){
		//int[][] distances = new int[10][10];
		Queue<Node> queue = new LinkedList<Node>();
		
		for(Point p : points){
			if(board.isEmpty(p)){
				marked[p.x][p.y] = true;
	            distTo[p.x][p.y] = 0;
	            queue.add(new Node(p));
			}
		}
		
		while(!queue.isEmpty()){
			Node r = queue.remove();
			for(Node n: r.getChild())
            {
                if(!marked[n.getLocation().x][n.getLocation().y])
                {
                    edgeTo[n] = r;
                	//queue.add(n);
                    //n.state = State.Visited;
                }
            }
		}
	}*/

	/*public void getQueenDistances(Board board,boolean colour){
		int[][] eval = new int[9][9];
		int[] possibleShortestDistances = new int[4];
		ArrayList<Point> points = new ArrayList<Point>();
		for(int i=0; i<9;i++){
			for(int j= 0; j<9;j++){
				if(board.getField()[i][j].equals(GameObject.Empty))
					points.add(new Point(i,j));
			}
		}
		getQueens(colour);
		//for each queen of 1 colour, go through all points 
		//with no gameobject and get the shortest distance
		//of each queen to each point
		//points that cannot be reached get value 5000
		for(Queen queen : queens){
			for(Point p : points){
				for(int i = 0; i<4;i++){
					ChildrenOf(p);
					possibleShortestDistances[i] = BFS(board,queen.getPosition(),p,moveLocations);
				}
				int min = 5001;
				for (int i=0; i<possibleShortestDistances.length; i++){
					   if (possibleShortestDistances[i] < min){
					      min = possibleShortestDistances[i];
					   }
					}
				eval[p.x][p.y] = min;
			}
		}
	}*/
	
	/*public void getKingDistances(Board board,boolean colour){
		int[][] eval2 = new int[9][9];
		int[] possibleShortestDistances = new int[4];
		ArrayList<Point> points = new ArrayList<Point>();
		for(int i=0; i<9;i++){
			for(int j= 0; j<9;j++){
				if(board.getField()[i][j].equals(GameObject.Empty))
					points.add(new Point(i,j));
			}
		}
		getQueens(colour);
		//for each queen of 1 colour, go through all points 
		//with no gameobject and get the shortest distance
		//of each queen to each point
		//points that cannot be reached get value 5000
		for(Queen queen : queens){
			for(Point p : points){
				for(int i = 0; i<4;i++){
					ChildrenOf2(p);
					possibleShortestDistances[i] = BFS(board,queen.getPosition(),p,kingMoveLocations);
				}
				int min = 5001;
				for (int i=0; i<possibleShortestDistances.length; i++){
					   if (possibleShortestDistances[i] < min){
					      min = possibleShortestDistances[i];
					   }
					}
				eval2[p.x][p.y] = min;
			}
		}
	}*/
	
	public int BFS(Board board, Point root, Point goal, Vector<Point> locations){
		Queue<Point> queue = new LinkedList<Point>();
		ArrayList<Point> v = new ArrayList<Point>();
		v.add(root);
		queue.add(root);
		while(!queue.isEmpty()){
			Point tmp = queue.remove();
			if(tmp.equals(goal)){
				return v.size();
			}
			for(Point e : locations){
				Point u = e;
				if(!v.contains(u)){
					v.add(u);
					queue.add(u);
				}
			}
		}
		return 5000;
	}
	
	private boolean ChildrenOf(Point p) {//fill moveLocations starting from point p
		boolean filled = false;
		while (!filled) {
			Point nextPoint = new Point(0, 0);
			nextPoint.setLocation(p.getX(), p.getY() + 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() + 1.0);
			}
			nextPoint.setLocation(p.getX() + 1.0, p.getY() + 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() + 1.0, nextPoint.getY() + 1.0);
			}
			nextPoint.setLocation(p.getX() + 1, p.getY());
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY());
			}
			nextPoint.setLocation(p.getX() + 1, p.getY() - 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY() - 1.0);
			}
			nextPoint.setLocation(p.getX(), p.getY() - 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() - 1.0);
			}
			nextPoint.setLocation(p.getX() - 1, p.getY() - 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() - 1.0);
			}
			nextPoint.setLocation(p.getX() - 1, p.getY());
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY());
			}
			nextPoint.setLocation(p.getX() - 1, p.getY() + 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() + 1.0);
			}
			if (moveLocations.size() != 0) {
				filled = true;
			} else {
				return false;
			}
		}
		return true;
	}
	
	private boolean ChildrenOf2(Point p) {//fill moveLocations starting from point p
		boolean filled = false;
		while (!filled) {
			Point nextPoint = new Point(0, 0);
			nextPoint.setLocation(p.getX(), p.getY() + 1.0);
			if (board.isEmpty(nextPoint) == true) {
				kingMoveLocations.add(nextPoint);
			}
			nextPoint.setLocation(p.getX() + 1.0, p.getY() + 1.0);
			if (board.isEmpty(nextPoint) == true) {
				kingMoveLocations.add(nextPoint);
			}
			nextPoint.setLocation(p.getX() + 1, p.getY());
			if (board.isEmpty(nextPoint) == true) {;
				kingMoveLocations.add(nextPoint);
			}
			nextPoint.setLocation(p.getX() + 1, p.getY() - 1.0);
			if (board.isEmpty(nextPoint) == true) {
				kingMoveLocations.add(nextPoint);
			}
			nextPoint.setLocation(p.getX(), p.getY() - 1.0);
			if (board.isEmpty(nextPoint) == true) {
				kingMoveLocations.add(nextPoint);
			}
			nextPoint.setLocation(p.getX() - 1, p.getY() - 1.0);
			if (board.isEmpty(nextPoint) == true) {
				kingMoveLocations.add(nextPoint);
			}
			nextPoint.setLocation(p.getX() - 1, p.getY());
			if (board.isEmpty(nextPoint) == true) {
				kingMoveLocations.add(nextPoint);
			}
			nextPoint.setLocation(p.getX() - 1, p.getY() + 1.0);
			if (board.isEmpty(nextPoint) == true) {
				kingMoveLocations.add(nextPoint);
			}
			if (kingMoveLocations.size() != 0) {
				filled = true;
			} else {
				return false;
			}
		}
		return true;
	}
	
	
}

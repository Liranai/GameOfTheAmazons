package evaluation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import model.Board;
import model.Queen;

public class Evaluation {

	/**
	 * Returns vector of Queens of given color
	 * 
	 * @param colour
	 *            boolean color
	 * @param board
	 *            board to use
	 * @return Vector<Queen> queens
	 */
	public static Vector<Queen> getQueens(boolean colour, Board board) {
		Vector<Queen> queens = new Vector<Queen>();
		for (Queen queen : board.getQueens()) {
			if (queen.isColor() == colour)
				queens.add(queen);
		}
		return queens;
	}

	/**
	 * Returns all empty and reachable locations by QueenMove from point
	 * 
	 * @param point
	 *            Start location
	 * @param board
	 *            board to use
	 * @return Vector<Point> locations
	 */
	public static Vector<Point> getLocations(Point point, Board board) {
		Vector<Point> locations = new Vector<Point>();

		for (int i = 0; i < Board.DIRECTIONS.length; i++) {
			Point tempPoint = new Point(point.x, point.y);
			while (tempPoint.x >= 0 && tempPoint.y >= 0 && tempPoint.x < 10 && tempPoint.y < 10) {
				tempPoint.translate(Board.DIRECTIONS[i][0], Board.DIRECTIONS[i][1]);
				if (board.isEmpty(tempPoint)) {
					locations.add(new Point(tempPoint.x, tempPoint.y));
				} else {
					break;
				}
			}
		}
		return locations;
	}

	/**
	 * Returns all empty points on the board
	 * 
	 * @param board
	 *            board to use
	 * @return Vector<Point> empty points
	 */
	public static Vector<Point> getEmptySquares(Board board) {
		Vector<Point> points = new Vector<Point>();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (board.isEmpty(new Point(i, j))) {
					points.add(new Point(i, j));
				}
			}
		}
		return points;
	}

	/*
	 * @return a vector with all points in the 10*10 board
	 */
	public static Vector<Point> getAllAvailablePoints(Board board) {
		Vector<Point> points = new Vector<Point>();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				points.add(new Point(i, j));
			}
		}
		return points;
	}

	/*
	 * goes through board and gets queen distances to all other points
	 */
	public static int[][] BFS(Board board, Point root) {
		Queue<Point> queue = new LinkedList<Point>();
		ArrayList<Point> v = new ArrayList<Point>();
		int[][] distances = new int[10][10];
		Point[][] previous = new Point[10][10];

		Vector<Point> allSquares = getAllAvailablePoints(board);
		for (Point o : allSquares) {
			distances[o.x][o.y] = Integer.MAX_VALUE;
			previous[o.x][o.y] = null;
		}

		v.add(root);
		queue.add(root);
		distances[root.x][root.y] = 0;
		previous[root.x][root.y] = root;
		Vector<Point> locations = getLocations(root, board);
		for (Point p : locations) {
			distances[p.x][p.y] = 1;
			previous[p.x][p.y] = root;
			v.add(p);
			queue.add(p);
		}
		while (!queue.isEmpty()) {
			Point tmp = queue.remove();
			Vector<Point> tmpLocations = getLocations(tmp, board);
			for (Point u : tmpLocations) {
				if (!v.contains(u)) {
					if (distances[u.x][u.y] > distances[tmp.x][tmp.y] + 1) {
						distances[u.x][u.y] = distances[tmp.x][tmp.y] + 1;
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
	 * compares queen distances and returns a vector with the shortest distances
	 * of one player to each cell in the board
	 */
	public static int[][] getQueenDistances(Board board, boolean colour) {
		int[][] eval = new int[10][10];
		Vector<Point> allSquares = getAllAvailablePoints(board);
		for (Point o : allSquares) {
			eval[o.x][o.y] = Integer.MAX_VALUE;
		}
		int[][] mob = new int[10][10];
		Vector<Queen> queens = getQueens(colour, board);
		for (Queen queen : queens) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					int[][] tmp = BFS(board, queen.getPosition());
					if (eval[j][k] == 1)
						mob[j][k]++;
					if (eval[j][k] > tmp[j][k]) {
						eval[j][k] = tmp[j][k];
					}
				}
			}
		}
		return eval;
	}

	public static int[][] calcMob(Board board, Point root) {
		int[][] mob = new int[10][10];
		Vector<Point> locations = getLocations(root, board);
		for (Point p : locations) {
			mob[p.x][p.y] = 1;
		}
		return mob;
	}

	/*
	 * Mob = the number of queens which can arrive at square x in one move.
	 */
	public static int[][] getMob(Board board, boolean colour) {
		int[][] mob = new int[10][10];
		Vector<Queen> queens = getQueens(colour, board);
		for (Queen queen : queens) {
			int[][] tmp = calcMob(board, queen.getPosition());
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					if (tmp[j][k] == 1)
						mob[j][k]++;
				}
			}
		}
		return mob;
	}
}

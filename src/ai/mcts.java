package ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Vector;

import logic.AmazonLogic;
import model.Board;
import model.Move;
import model.Queen;

public class mcts extends ArtificialIntelligence{
	
	ArrayList<MCTSNode> firstChildren = new ArrayList<MCTSNode>();
	
	public mcts(boolean color) {
		super(color);
		// TODO Auto-generated constructor stub
	}
	public int depth = 4;
	public int itterations = 50;



	private Board board;

	
	public Move getMove(){
		getChildren();
		getValues();
		Board newBoard = move();
		Queen queen = null;
		Point moveLocation = null;
		Point shootLocation = null;
		for(int i=0;i<newBoard.getQueens().size();i++){
			System.out.println(newBoard.getQueens().get(i).getPosition());
		}
		for(int i=0; i<newBoard.getQueens().size();i++){
			if(newBoard.getQueens().get(i).getPosition() != board.getQueens().get(i).getPosition()){
				queen = newBoard.getQueens().get(i);
				moveLocation = newBoard.getQueens().get(i).getPosition();
			}
		}
		for(int i=0;i<newBoard.getArrows().size();i++){
			System.out.println("-----");
			System.out.println(newBoard.getArrows().get(i));
			System.out.println("-----");
		}
		shootLocation = newBoard.getArrows().get(newBoard.getArrows().size()-1);
		return new Move(queen, moveLocation, shootLocation);
				
	}

	public void getChildren(){
		for(int i=0;i<board.getQueens().size();i++){
			Vector<Point> moveLocations = new Vector<Point>();
			Queen queen = board.getQueens().get(i);
			Point nextPoint = new Point(0, 0);
			nextPoint.setLocation(queen.getPosition().getX(), queen.getPosition().getY() + 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() + 1.0);
			}
			nextPoint.setLocation(queen.getPosition().getX() + 1.0, queen.getPosition().getY() + 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() + 1.0, nextPoint.getY() + 1.0);
			}
			nextPoint.setLocation(queen.getPosition().getX() + 1, queen.getPosition().getY());
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY());
			}
			nextPoint.setLocation(queen.getPosition().getX() + 1, queen.getPosition().getY() - 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY() - 1.0);
			}
			nextPoint.setLocation(queen.getPosition().getX(), queen.getPosition().getY() - 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() - 1.0);
			}
			nextPoint.setLocation(queen.getPosition().getX() - 1, queen.getPosition().getY() - 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() - 1.0);
			}
			nextPoint.setLocation(queen.getPosition().getX() - 1, queen.getPosition().getY());
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY());
			}
			nextPoint.setLocation(queen.getPosition().getX() - 1, queen.getPosition().getY() + 1.0);
			while (board.isEmpty(nextPoint) == true) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				moveLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() + 1.0);
			}
			for(int j=0;j<moveLocations.size();j++){
				Vector<Point> shootLocations = new Vector<Point>();
				nextPoint = new Point(0, 0);
				nextPoint.setLocation(moveLocations.get(j).getX(), moveLocations.get(j).getY() + 1.0);
				while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == queen.getPosition().getX() && nextPoint.getY() == queen.getPosition().getY())) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					shootLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() + 1.0);
				}
				nextPoint.setLocation(moveLocations.get(j).getX() + 1.0, moveLocations.get(j).getY() + 1.0);
				while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == queen.getPosition().getX() && nextPoint.getY() == queen.getPosition().getY())) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					shootLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() + 1.0, nextPoint.getY() + 1.0);
				}
				nextPoint.setLocation(moveLocations.get(j).getX() + 1, moveLocations.get(j).getY());
				while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == queen.getPosition().getX() && nextPoint.getY() == queen.getPosition().getY())) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					shootLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY());
				}
				nextPoint.setLocation(moveLocations.get(j).getX() + 1, moveLocations.get(j).getY() - 1.0);
				while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == queen.getPosition().getX() && nextPoint.getY() == queen.getPosition().getY())) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					shootLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY() - 1.0);
				}
				nextPoint.setLocation(moveLocations.get(j).getX(), moveLocations.get(j).getY() - 1.0);
				while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == queen.getPosition().getX() && nextPoint.getY() == queen.getPosition().getY())) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					shootLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() - 1.0);
				}
				nextPoint.setLocation(moveLocations.get(j).getX() - 1, moveLocations.get(j).getY() - 1.0);
				while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == queen.getPosition().getX() && nextPoint.getY() == queen.getPosition().getY())) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					shootLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() - 1.0);
				}
				nextPoint.setLocation(moveLocations.get(j).getX() - 1, moveLocations.get(j).getY());
				while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == queen.getPosition().getX() && nextPoint.getY() == queen.getPosition().getY())) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					shootLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY());
				}
				nextPoint.setLocation(moveLocations.get(j).getX() - 1, moveLocations.get(j).getY() + 1.0);
				while (board.isEmpty(nextPoint) == true || (nextPoint.getX() == queen.getPosition().getX() && nextPoint.getY() == queen.getPosition().getY())) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					shootLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() + 1.0);
				}
				for(int k=0;k<shootLocations.size();k++){
					Board child = new Board();
					child = board.clone();
					child.move(queen, moveLocations.get(j), shootLocations.get(k));
					MCTSNode g = new MCTSNode(child);
					firstChildren.add(g);
				}
			}
		}
	}
	
	public void getValues(){
		System.out.println(firstChildren.size());
		for(int i=0;i<firstChildren.size();i++){
			System.out.println("child number " + i);
			for(int j=0;j<itterations;j++){
				//System.out.println("itteration number " + j);
				firstChildren.get(i).addToAverage(mctssearch(firstChildren.get(i), depth, !color));
			}
		}
	}
	
	public double mctssearch(MCTSNode root, int g, boolean turn){
		double result = 0.0;
		if(g != 0){
			MCTSNode newNode = randomMove(root, !turn);
			result = mctssearch(newNode, g-1, turn);
		}else{
			result = root.calculateValue(!turn);
		}
		return result;
	}
	
	//TODO: optimize randomMove later
	/*public static Vector<Point> getLocations(Point point, Board board) {
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
	}*/
	
	public MCTSNode randomMove(MCTSNode root, boolean turn){
		Vector<Queen> queens = new Vector<Queen>();
		for(int i = 0; i<root.getBoard().getQueens().size();i++){
			if(root.getBoard().getQueens().get(i).isColor()== turn){
				queens.add(root.getBoard().getQueens().get(i));
			}
		}

		Vector<Point> moveLocations = new Vector<Point>();
		Queen chosenQueen = null;
		boolean moveMade = false;
		while (moveMade == false) {
			int q = (int) (Math.random() * queens.size());
			Queen queen = queens.get(q);
			boolean filled = false;
			while (filled == false) {
				Point nextPoint = new Point(0, 0);
				nextPoint.setLocation(queen.getPosition().getX(), queen.getPosition().getY() + 1.0);
				while (root.getBoard().isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() + 1.0);
				}
				nextPoint.setLocation(queen.getPosition().getX() + 1.0, queen.getPosition().getY() + 1.0);
				while (root.getBoard().isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() + 1.0, nextPoint.getY() + 1.0);
				}
				nextPoint.setLocation(queen.getPosition().getX() + 1, queen.getPosition().getY());
				while (root.getBoard().isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY());
				}
				nextPoint.setLocation(queen.getPosition().getX() + 1, queen.getPosition().getY() - 1.0);
				while (root.getBoard().isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY() - 1.0);
				}
				nextPoint.setLocation(queen.getPosition().getX(), queen.getPosition().getY() - 1.0);
				while (root.getBoard().isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() - 1.0);
				}
				nextPoint.setLocation(queen.getPosition().getX() - 1, queen.getPosition().getY() - 1.0);
				while (root.getBoard().isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() - 1.0);
				}
				nextPoint.setLocation(queen.getPosition().getX() - 1, queen.getPosition().getY());
				while (root.getBoard().isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY());
				}
				nextPoint.setLocation(queen.getPosition().getX() - 1, queen.getPosition().getY() + 1.0);
				while (root.getBoard().isEmpty(nextPoint) == true) {
					Point aPoint = new Point();
					aPoint.setLocation(nextPoint);
					moveLocations.add(aPoint);
					nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() + 1.0);
				}
				if (moveLocations.size() != 0) {
					filled = true;
				} else {
					queen = queens.get((int) (Math.random() * queens.size()));
				}
			}
			if (moveLocations.size() != 0) {
				moveMade = true;
				chosenQueen = queen;
			}
		}
			Point chosenMove =  moveLocations.get((int) (Math.random() * moveLocations.size()));
			Vector<Point> shootLocations = new Vector<Point>();
			Point nextPoint = new Point(0, 0);
			nextPoint.setLocation(chosenMove.getX(), chosenMove.getY() + 1.0);
			while (root.getBoard().isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				shootLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() + 1.0);
			}
			nextPoint.setLocation(chosenMove.getX() + 1.0,chosenMove.getY() + 1.0);
			while (root.getBoard().isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				shootLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() + 1.0, nextPoint.getY() + 1.0);
			}
			nextPoint.setLocation(chosenMove.getX() + 1, chosenMove.getY());
			while (root.getBoard().isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				shootLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY());
			}
			nextPoint.setLocation(chosenMove.getX() + 1, chosenMove.getY() - 1.0);
			while (root.getBoard().isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				shootLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() + 1, nextPoint.getY() - 1.0);
			}
			nextPoint.setLocation(chosenMove.getX(), chosenMove.getY() - 1.0);
			while (root.getBoard().isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				shootLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX(), nextPoint.getY() - 1.0);
			}
			nextPoint.setLocation(chosenMove.getX() - 1, chosenMove.getY() - 1.0);
			while (root.getBoard().isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				shootLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() - 1.0);
			}
			nextPoint.setLocation(chosenMove.getX() - 1, chosenMove.getY());
			while (root.getBoard().isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				shootLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY());
			}
			nextPoint.setLocation(chosenMove.getX() - 1, chosenMove.getY() + 1.0);
			while (root.getBoard().isEmpty(nextPoint) == true || (nextPoint.getX() == chosenQueen.getPosition().getX() && nextPoint.getY() == chosenQueen.getPosition().getY())) {
				Point aPoint = new Point();
				aPoint.setLocation(nextPoint);
				shootLocations.add(aPoint);
				nextPoint.setLocation(nextPoint.getX() - 1, nextPoint.getY() + 1.0);
			}
			
		Point chosenShoot = shootLocations.get((int) (Math.random() * shootLocations.size()));
		Board newBoard = new Board();
		newBoard = root.getBoard().clone();
		Point x = chosenQueen.getPosition();
		Queen newChosenQueen = null;
		for(int i=0;i<newBoard.getQueens().size();i++){
			if(newBoard.getQueens().get(i).getPosition().getX() == chosenQueen.getPosition().getX() && newBoard.getQueens().get(i).getPosition().getY() == chosenQueen.getPosition().getY()){
				newChosenQueen = newBoard.getQueens().get(i);
			}
		}
		newBoard.move(newChosenQueen, chosenMove, chosenShoot);

		MCTSNode newNode = new MCTSNode(newBoard);
		return newNode;
	}
	
	public Board move(){
		MCTSNode max = firstChildren.get(0);
		for(int i = 0; i<firstChildren.size();i++){
			if(max.getAverage() < firstChildren.get(i).getAverage()){
				max = firstChildren.get(i);
				System.out.println("choosen = " + i);
				System.out.println(max.getAverage());
			}
		}
		
		return max.getBoard();
	}
	
	@Override
	public void update(Observable obser, Object obj) {
		this.board = ((AmazonLogic) obser).getBoard();
		if (((AmazonLogic) obser).isCurrentTurn() == color) {
			Move move = getMove();
			move.validate(board);
			
			System.out.println("Q: " + move.getQueen().getPosition() + " T: " + move.getTarget() + " A: " + move.getArrow());
			
			board.move(move);
			((AmazonLogic) obser).setCurrentTurn(!color);
		}
		((AmazonLogic) obser).getGUI().repaint();
	}
}
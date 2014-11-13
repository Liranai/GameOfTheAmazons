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
	public int itterations = 100;



	private Board board;

	
	public Move getMove(){
		getChildren();
		getValues();
		Board newBoard = move();
		Queen queen = null;
		Point moveLocation = null;
		Point shootLocation = null;
		for(int i=0; i<newBoard.getQueens().size();i++){
			if(newBoard.getQueens().get(i).getPosition() != board.getQueens().get(i).getPosition()){
				queen = board.getQueens().get(i);
				moveLocation = newBoard.getQueens().get(i).getPosition();
			}
		}
		shootLocation = newBoard.getArrows().get(newBoard.getArrows().size());
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
					child = board;
					child.move(queen, moveLocations.get(j), shootLocations.get(k));
					MCTSNode g = new MCTSNode(child);
					firstChildren.add(g);
				}
			}
		}
	}
	
	public void getValues(){
		for(int i=0;i<firstChildren.size();i++){
			for(int j=0;j<itterations;j++){
				firstChildren.get(i).addToAverage(mctssearch(firstChildren.get(i), depth));
			}
		}
	}
	
	public double mctssearch(MCTSNode root, int g){
		double result = 0.0;
		if(g != 0){
			boolean turn;
			if(g%2 == 0){
				turn = color;
			}else{turn = !color;}
			MCTSNode newNode = randomMove(root, turn);
			result = mctssearch(newNode, g-1);
		}else{
			result = root.calculateValue(color);
		}
		
		return result;
	}
	
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
		newBoard = root.getBoard();
		newBoard.move(chosenQueen, chosenMove, chosenShoot);
		MCTSNode newNode = new MCTSNode(newBoard);
		return newNode;
	}
	
	public Board move(){
		MCTSNode max = firstChildren.get(0);
		for(int i = 0; i<firstChildren.size();i++){
			if(max.getAverage() < firstChildren.get(i).getAverage()){
				max = firstChildren.get(i);
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
			board.move(move);
			((AmazonLogic) obser).setCurrentTurn(!color);
		}
		((AmazonLogic) obser).getGUI().repaint();
	}
}
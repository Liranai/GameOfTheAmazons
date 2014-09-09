package game;

public class Board {

	public Board() {
		// TODO Auto-generated constructor stub
	}
	
	private final int BOARDSIZE = 10;
	public int getSize(){
		return BOARDSIZE;
	}
	
	private Cell[][] Board = new Cell[BOARDSIZE][BOARDSIZE];
	
	public void initGame(){
		Board[3][0] = new Amazon(3,0,w);
		Board[6][0] = new Amazon(6,0,w);
		Board[0][3] = new Amazon(0,3,w);
		Board[9][3] = new Amazon(9,3,w);
		Board[3][9] = new Amazon(3,9,b);
		Board[0][6] = new Amazon(0,6,b);
		Board[9][6] = new Amazon(9,6,b);
		Board[6][9] = new Amazon(6,9,b);
	}
}

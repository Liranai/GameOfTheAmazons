package game;

public class Cell {

	public Cell(int x, int y) {
		// TODO Auto-generated constructor stub
	}
	private GameObject element = null;
	private int[] coordinates = new int[2];
	
	public int[] getCoordinates(){
		return coordinates;
	}
	
	public void setCoordinates(int x, int y){
		coordinates[0] = x;
		coordinates[1] = y;
	}
	
	public void setElement(GameObject e){
		element = e;
	}
	
	public GameObject getElement(){
		return element;
	}
	
	
	

}

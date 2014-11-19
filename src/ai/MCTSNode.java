package ai;

import java.util.ArrayList;

import evaluation.Mobility;
import lombok.Getter;
import lombok.Setter;
import model.Board;
 @Getter
public class MCTSNode {
	private Board board;
	private int times = 0;
	private MCTSNode parent;
	
	private Double average = 0.0;
	private ArrayList<MCTSNode> children = new ArrayList<MCTSNode>();
	
	public MCTSNode(Board board){
		this.board = board;
	}
	public void addToAverage(double amount){
		times++;
		if(average == 0.0){
			average = amount;
		}else{
			average = (average*(times-1)+amount)/times;
		}
	}
	int calculateValue(boolean color){
		return Mobility.getMobilityFeature(color, board);
	}
}

package ai;

import lombok.Getter;
import model.Board;
import model.Move;

@Getter
public abstract class ArtificialIntelligence {

	protected boolean color;
	protected long MIN_TIME = 0, AVG_TIME = 0, MAX_TIME = 0;
	protected long MOVES = 0;
	protected long MIN_NODES = 0, AVG_NODES = 0, MAX_NODES = 0;

	public ArtificialIntelligence(boolean color) {
		this.color = color;
	}

	public void calculateAverage() {
		AVG_TIME = AVG_TIME / MOVES;
		AVG_NODES = AVG_NODES / MOVES;
	}

	public abstract String getName();

	public abstract Move getMove(Board board);
}
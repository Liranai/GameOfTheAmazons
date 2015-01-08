package ai;

import lombok.Getter;
import model.Board;
import model.Move;

public abstract class ArtificialIntelligence {

	@Getter
	protected boolean color;

	public ArtificialIntelligence(boolean color) {
		this.color = color;
	}

	public abstract Move getMove(Board board);
}
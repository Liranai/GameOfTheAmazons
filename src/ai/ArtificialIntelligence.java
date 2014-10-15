package ai;

import java.util.Observable;
import java.util.Observer;

public abstract class ArtificialIntelligence implements Observer {

	protected boolean color;

	public ArtificialIntelligence(boolean color) {
		this.color = color;
	}

	public abstract void update(Observable arg0, Object arg1);
}
package ai;

import java.util.Observable;
import java.util.Observer;

public abstract class ArtificialIntelligence implements Observer {

	protected boolean color;

	/**
	 * Basic AI structure.
	 * 
	 * @param color
	 *            Color for which the AI plays
	 */
	public ArtificialIntelligence(boolean color) {
		this.color = color;
	}

	/**
	 * Method that creates a Move and makes it
	 */
	public abstract void update(Observable arg0, Object arg1);
}
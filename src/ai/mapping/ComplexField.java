package ai.mapping;

import java.util.Vector;

import model.Queen;

public class ComplexField {

	private int x, y;
	private Vector<Pair<Queen, Double>> queenValues;

	public ComplexField(int x, int y) {
		this.x = x;
		this.y = y;
		queenValues = new Vector<Pair<Queen, Double>>();
	}

	public boolean addQueen(Pair<Queen, Double> newPair) {
		for (Pair<Queen, Double> pair : queenValues) {
			if (pair.getFirst().equals(newPair.getFirst()))
				return false;
		}

		queenValues.add(newPair);
		return true;
	}
}

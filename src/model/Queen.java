package model;

import java.awt.Point;

import lombok.Getter;

@Getter
public class Queen {

	private Point position;
	private boolean color;

	public Queen(Point p, boolean color) {
		position = p;
		this.color = color;
	}

	public boolean move(Point p) {
		if ((p.x >= 0 && p.x < 10 && p.y >= 0 && p.y < 10)
				&& ((p.x == position.x && p.y != position.y) || (p.y == position.y && p.x != position.x) || (Math.abs(p.x - position.x) == Math.abs(p.y - position.y)))) {
			position = p;
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		return (((Queen) obj).color == color && ((Queen) obj).position == position);
	}

	public Queen clone() {
		return new Queen(new Point(position.x, position.y), color);
	}
}

package de.lebk.madn;

public class Coordinate {

	private int x;
	private int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean equals(Coordinate other) {
		return ((this.x == other.x) && (this.y == other.y));
	}
	
	public String toString() {
		return String.format("(%d/%d)", this.x, this.y);
	}
	
}
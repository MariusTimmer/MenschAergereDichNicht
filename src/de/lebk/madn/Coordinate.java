package de.lebk.madn;

/**
 * This class stores coordinates which will be used for the fields on the board
 * @author Marius Timmer <admin@MariusTimmer.de>
 * @date 2015/09/16
 */

public class Coordinate {

	private int x;  // Store the X-Position
	private int y;  // Store the Y-Position
	
	/**
	 * Store x- and y-position for this coordinate
	 * @param x X-Position
	 * @param y Y-Position
	 */
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
	
	/**
	 * Checks if the Coordinate other has the same position like this
	 * @param other Coordinate to compare
	 * @return True if the position is the same or false
	 */
	public boolean equals(Coordinate other) {
		return ((this.x == other.x) && (this.y == other.y));
	}
	
	public String toString() {
		return String.format("(%d/%d)", this.x, this.y);
	}
	
}
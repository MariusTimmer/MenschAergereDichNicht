package de.timmer.menschenaergeredichnicht.gui;

/**
 * This class stores coordinates (X and Y)
 * @author Marius Timmer
 * @date 2015-09-15
 */

public class Coordinate {

    protected int x;
    protected int y;
    
    /**
     * Creates a coordinate-object that stores x and y positions
     * @param x X-Position
     * @param y Y-Position
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Returns the X-Position
     * @return X-Position
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * Returns the Y-Position
     * @return Y-Position
     */
    public int getY() {
        return this.y;
    }
    
    public String toString() {
        return String.format("(%d/%d)", this.x, this.y);
    }

}
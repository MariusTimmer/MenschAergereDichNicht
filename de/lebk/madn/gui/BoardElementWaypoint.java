package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import java.awt.Color;
import java.awt.Graphics;

public class BoardElementWaypoint extends BoardElement {

    protected Coordinate alternative = null;

    /**
     * Generates a Waypoint with alternative
     * @param board The board on which the element will be placed
     * @param position The position of the element
     * @param nextElement The coordinate of the following element
     * @param alternative The coordinate of the laternative following element
     */
    public BoardElementWaypoint(Board board, Coordinate position, Coordinate nextElement, Coordinate alternative)
    {
        this(board, position, nextElement);
        this.alternative = alternative;
    }

    /**
     * Generates a Waypoint with alternative
     * @param board The board on which the element will be placed
     * @param position The position of the element
     * @param nextElement The coordinate of the following element
     */
    public BoardElementWaypoint(Board board, Coordinate position, Coordinate nextElement) {
        super(board, position, nextElement);
    }

    /**
     * Sets an alternative following point
     * @param c alternative
     */
    public void setAlternative(Coordinate c)
    {
	this.alternative = c;
    }

    /**
     * Checks if this element has an alternative following
     * @return True if there is an alternative or false
     */
    public boolean hasAlternative()
    {
	return (this.alternative != null);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawField(g, Color.BLACK, Color.WHITE);
        this.drawOccupier(g);
    }
}

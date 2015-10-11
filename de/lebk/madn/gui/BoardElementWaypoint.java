package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import java.awt.Color;
import java.awt.Graphics;

public class BoardElementWaypoint extends BoardElement {

    public BoardElementWaypoint(Board board, Coordinate position, Coordinate nextElement) {
        super(board, position, nextElement);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawField(g, Color.BLACK, Color.WHITE);
        this.drawOccupier(g);
    }
}

package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import java.awt.Color;
import java.awt.Graphics;

public class BoardElementHome extends BoardElementColored {
    
    public BoardElementHome(Board board, Coordinate nextElement, Color color) {
        super(board, nextElement, color);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawField(g, Color.BLACK, this.color);
        this.drawOccupier(g);
    }
    
}

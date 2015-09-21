package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;

abstract public class BoardElement extends JComponent {
    
    public static enum BOARD_ELEMENT_TYPES {HOME, WAYPOINT, GOAL};
    public static final int DEFAULT_CIRCLE_PADDING = 10;
    protected Coordinate nextElement;
    
    public BoardElement(Coordinate nextElement) {
        this.nextElement = nextElement;
    }
    
    public Coordinate getNextElementPosition() {
        return this.nextElement;
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.fillOval(DEFAULT_CIRCLE_PADDING, DEFAULT_CIRCLE_PADDING, this.getWidth() - (2 * DEFAULT_CIRCLE_PADDING), this.getHeight() - (2 * DEFAULT_CIRCLE_PADDING));
        g.setColor(Color.BLACK);
        g.drawOval(DEFAULT_CIRCLE_PADDING, DEFAULT_CIRCLE_PADDING, this.getWidth() - (2 * DEFAULT_CIRCLE_PADDING), this.getHeight() - (2 * DEFAULT_CIRCLE_PADDING));
        
    }
    
}

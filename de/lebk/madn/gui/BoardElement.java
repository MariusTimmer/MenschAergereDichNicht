package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import javax.swing.JComponent;

abstract public class BoardElement extends JComponent {
    
    public static enum BOARD_ELEMENT_TYPES {HOME, WAYPOINT, GOAL};
    protected Coordinate nextElement;
    
    public BoardElement(Coordinate nextElement) {
        this.nextElement = nextElement;
    }
    
    public Coordinate getNextElementPosition() {
        return this.nextElement;
    }
    
}

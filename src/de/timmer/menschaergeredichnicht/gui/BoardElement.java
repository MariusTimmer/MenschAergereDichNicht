package de.timmer.menschaergeredichnicht.gui;

import java.swing.JComponent;

abstract class BoardElement extends JComponent {

    public static final enum TYPES = {HOME, WAYPOINT, GOAL};
    protected Coordinate next;
    
    /**
     * Creates a board-element in the gui
     * @param next the coordinate of the next boardelement
     */
    public BoardElement(Coordinate next) {
        this.next = next;
    }
    
    /**
     * Tells you if there is a next field
     * @return Has next field
     */
    public boolean hasNext() {
        return (this.next != null);
    }
    
    public Coordinate getNext() {
        return this.next;
    }

}
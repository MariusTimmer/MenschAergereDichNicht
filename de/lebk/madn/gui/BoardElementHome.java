package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import java.awt.Color;

public class BoardElementHome extends BoardElement {

    protected Color color;
    
    public BoardElementHome(Coordinate nextElement, Color color) {
        super(nextElement);
        this.color = color;
    }
    
}

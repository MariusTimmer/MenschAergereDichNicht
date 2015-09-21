package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import java.awt.Color;
import java.awt.Graphics;

public class BoardElementGoal extends BoardElement {

    protected Color color;

    public BoardElementGoal(Coordinate nextElement, Color color) {
        super(nextElement);
        this.color = color;
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(this.color);
        g.drawOval(DEFAULT_CIRCLE_PADDING, DEFAULT_CIRCLE_PADDING, this.getWidth() - (2 * DEFAULT_CIRCLE_PADDING), this.getHeight() - (2 * DEFAULT_CIRCLE_PADDING));
    }
    
}

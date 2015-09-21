package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import java.awt.BasicStroke;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
    
    protected void drawField(Graphics g, Color foreground, Color background) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        if (background != null) {
            // Use the given color
            g2.setColor(background);
        } else {
            // Use a default white color
            g2.setColor(Color.WHITE);
        }
        g2.fillOval(DEFAULT_CIRCLE_PADDING, DEFAULT_CIRCLE_PADDING, this.getWidth() - (2 * DEFAULT_CIRCLE_PADDING), this.getHeight() - (2 * DEFAULT_CIRCLE_PADDING));
        // Draw the outer circle
        g2.setColor(foreground);
        g2.drawOval(DEFAULT_CIRCLE_PADDING, DEFAULT_CIRCLE_PADDING, this.getWidth() - (2 * DEFAULT_CIRCLE_PADDING), this.getHeight() - (2 * DEFAULT_CIRCLE_PADDING));
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.drawField(g, Color.BLACK, null);
    }
    
}

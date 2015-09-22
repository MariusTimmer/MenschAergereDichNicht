package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import de.lebk.madn.model.Player;
import de.lebk.madn.model.Figur;
import java.awt.BasicStroke;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

abstract public class BoardElement extends JComponent {
    
    public static enum		BOARD_ELEMENT_TYPES	{HOME, WAYPOINT, GOAL};
    public static final int	FIELD_BORDER_WIDTH	= 1;
    private static final int	FIGURE_RADIUS		= 8;
    public static final int	DEFAULT_CIRCLE_PADDING	= 11;
    protected Coordinate	nextElement;
    protected Figur		occupier		= null; /* The figure that uses this field */
    
    public BoardElement(Coordinate nextElement) {
        this.nextElement = nextElement;
    }
    
    public Coordinate getNextElementPosition() {
        return this.nextElement;
    }

    /**
     * 
     *
     */
    public Coordinate getNextCoordinate() {
        return this.nextElement;
    }
    
    /**
     * Returns the figure that is using this field
     * @return Figur occupier
     */
    public Figur getOccupier() {
        return this.occupier;
    }
    
    /**
     * Returns the information weather this field is used by a figure
     * @return True if the field is occupied or false if it is available
     */
    public boolean isOccupied() {
        return (this.occupier != null);
    }
    
    /**
     * Occupie this field if it is available
     * @param figure The new occupier
     * @return True if the new occupier is on the field or false if the field was used already
     */
    public boolean occupie(Figur figure) {
        if (!this.isOccupied()) {
            // Field is available
            this.occupier = figure;
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Checks if the field is occupied by the given player
     * @param player The player to compare with the occupier
     * @return True if the occupier belongs to the given player or false
     */
    public boolean isOccupiedByPlayer(Player player) {
        if (!this.isOccupied()) {
            return false;
        } else {
            if (player.getColor() == this.occupier.getColor()) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    protected void drawField(Graphics g, Color foreground, Color background) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(FIELD_BORDER_WIDTH));
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
        if (this.isOccupied()) {
            // draw this field as occupied
            g2.setColor(Color.BLACK);
            g2.drawOval(DEFAULT_CIRCLE_PADDING + FIGURE_RADIUS, DEFAULT_CIRCLE_PADDING + FIGURE_RADIUS, this.getWidth() - (2 * (FIGURE_RADIUS + DEFAULT_CIRCLE_PADDING)), this.getHeight() - (2 * (FIGURE_RADIUS + DEFAULT_CIRCLE_PADDING)));
            g2.setColor(this.occupier.getColor());
            g2.fillOval(DEFAULT_CIRCLE_PADDING + FIGURE_RADIUS, DEFAULT_CIRCLE_PADDING + FIGURE_RADIUS, this.getWidth() - (2 * (FIGURE_RADIUS + DEFAULT_CIRCLE_PADDING)), this.getHeight() - (2 * (FIGURE_RADIUS + DEFAULT_CIRCLE_PADDING)));
        }
	
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.drawField(g, Color.BLACK, null);
    }
    
}

package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import de.lebk.madn.Logger;
import de.lebk.madn.model.Figur;
import de.lebk.madn.model.Player;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

abstract public class BoardElement extends JComponent implements MouseListener {
    
    public static enum      BOARD_ELEMENT_TYPES {HOME, WAYPOINT, GOAL};
    public static final int FIELD_BORDER_WIDTH  = 1;
    public static int       CIRCLE_PADDING      = 0;
    protected Coordinate    position;
    protected Coordinate    nextElement;
    protected Figur         occupier            = null; /* The figure that uses this field */
    protected Board         board;
    
    public BoardElement(Board board, Coordinate position, Coordinate nextElement) {
        this.board = board;
        this.position = position;
        this.nextElement = nextElement;
        this.addMouseListener(this); // Register this as its own mouselistener
    }
    
    public Coordinate getNextElementPosition() {
        return this.nextElement;
    }
	
    public Coordinate getPosition() {
        return this.position;
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
            this.setOccupierPrivate(figure);
            return true;
        }
        return false;
    }
    
    private void setOccupierPrivate(Figur figure) {
        this.occupier = figure;
        this.revalidate();
        this.repaint();
    }
    
    public void occupierLeaves() {
        this.setOccupierPrivate(null);
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
        g2.fillOval(CIRCLE_PADDING, CIRCLE_PADDING, this.getWidth() - (2 * CIRCLE_PADDING), this.getHeight() - (2 * CIRCLE_PADDING));
        // Draw the outer circle
        g2.setColor(foreground);
        g2.drawOval(CIRCLE_PADDING, CIRCLE_PADDING, this.getWidth() - (2 * CIRCLE_PADDING), this.getHeight() - (2 * CIRCLE_PADDING));
    }
    
    protected void drawOccupier(Graphics g) {
		if (this.isOccupied()) {
            // draw this field as occupied
            Graphics2D g2 = (Graphics2D) g;
            int figure_radius = Math.round((this.getWidth() - (2 * CIRCLE_PADDING)) / 4);
            g2.setStroke(new BasicStroke(3));
            g2.setColor(Color.BLACK);
            g2.drawOval(CIRCLE_PADDING + figure_radius, CIRCLE_PADDING + figure_radius, this.getWidth() - (2 * (figure_radius + CIRCLE_PADDING)), this.getHeight() - (2 * (figure_radius + CIRCLE_PADDING)));
            g2.setColor(this.occupier.getColor());
            g2.fillOval(CIRCLE_PADDING + figure_radius, CIRCLE_PADDING + figure_radius, this.getWidth() - (2 * (figure_radius + CIRCLE_PADDING)), this.getHeight() - (2 * (figure_radius + CIRCLE_PADDING)));
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        if (this.isOccupied()) {
	    this.board.moveFigur(this.position, this.occupier);
	}
    }
    
    @Override
    public void mouseEntered(MouseEvent me) {
        // Mouse is over the element
    }
    
    @Override
    public void mouseExited(MouseEvent me) {
        // Mouse left this element
    }
    
    @Override
    public void mousePressed(MouseEvent me) {
	// Mousekey pressed
    }
    
    @Override
    public void mouseReleased(MouseEvent me) {
	// Mousekey released
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
    @Override
    public String toString() {
        return String.format("%s", this.getClass().getSimpleName());
    }
    
}

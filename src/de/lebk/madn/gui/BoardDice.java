package de.lebk.madn.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

public class BoardDice extends JComponent implements MouseListener {
    
    private int number = 0;
    private Color color = new Color(255, 255, 255);// new Color(239, 239, 239);
    protected Board board;
    
    public BoardDice(Board board) {
        super();
        this.board = board;
        this.addMouseListener(this);
    }
    
    /**
     * Sets the number and updates the GUI
     * @param number 
     */
    public void setNumber(int number) {
        this.number = number;
        this.revalidate();
        this.repaint();
    }
    
    public void setColor(Color color) {
        this.color = color;
        this.revalidate();
        this.repaint();
    }

    public int getNumber() {
        return this.number;
    }
    
    /**
     * Draw the GUI
     * @param g 
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        /**
         * here the colors will be prepared. The dice will be filled
         * with the original color of the current player. The border
         * an the points of the dice will be in the same color just
         * a bit darker.
         * Delta describes the difference of the border and point
         * color from the original color.
         */
        float delta = 0.33f;
        Color background_color = new Color(
            Math.min(Math.round(this.color.getRed() * delta), 255),
            Math.min(Math.round(this.color.getGreen() * delta), 255),
            Math.min(Math.round(this.color.getBlue() * delta), 255)
        );
        g2.setStroke(new BasicStroke(BoardElement.FIELD_BORDER_WIDTH));
        /**
         * First we draw the dice with the original color as background.
         */
        g2.setColor(this.color);
        g2.fillRoundRect(BoardElement.CIRCLE_PADDING, BoardElement.CIRCLE_PADDING, this.getWidth() - (2 * BoardElement.CIRCLE_PADDING), this.getHeight() - (2 * BoardElement.CIRCLE_PADDING), BoardElement.CIRCLE_PADDING, BoardElement.CIRCLE_PADDING);
        /**
         * After drawing the background we have to draw the border line
         * in the darker color. This color will be used to draw the
         * points on the dice as well then.
         */
        g2.setColor(background_color);
        g2.drawRoundRect(BoardElement.CIRCLE_PADDING, BoardElement.CIRCLE_PADDING, this.getWidth() - (2 * BoardElement.CIRCLE_PADDING), this.getHeight() - (2 * BoardElement.CIRCLE_PADDING), BoardElement.CIRCLE_PADDING / 2, BoardElement.CIRCLE_PADDING / 2);
        this.drawPoints(g2, background_color);
    }
    
    protected void drawPoints(Graphics g2, Color point_color) {
        g2.setColor(point_color);
        int dotSize = (int)(this.getWidth() / 10);
        int number = this.number;
        // Top Left
        if(number >= 2) {
            this.drawPoint(g2, this.getWidth()/3-dotSize/2, this.getHeight()/3-dotSize/2, dotSize, dotSize);
        }
        // Top Center
        if(number >= 8) {
            this.drawPoint(g2, this.getWidth()/2-dotSize/2, this.getHeight()/3-dotSize/2, dotSize, dotSize);
        }
        // Top Right
        if(number >= 4) {
            this.drawPoint(g2, this.getWidth()/3*2-dotSize/2, this.getHeight()/3-dotSize/2, dotSize, dotSize);
        }
        // Middle Left
        if(number >= 6) {
            this.drawPoint(g2, this.getWidth()/3-dotSize/2, this.getHeight()/2-dotSize/2, dotSize, dotSize);
        }
        // Middle Center
        if(number % 2 == 1) {
            this.drawPoint(g2, this.getWidth()/2-dotSize/2, this.getHeight()/2-dotSize/2, dotSize, dotSize);
        }
        // Middle Right
        if(number >= 6) {
            this.drawPoint(g2, this.getWidth()/3*2-dotSize/2, this.getHeight()/2-dotSize/2, dotSize, dotSize);
        }
        // Bottom Left
        if(number >= 4) {
            this.drawPoint(g2, this.getWidth()/3-dotSize/2, this.getHeight()/3*2-dotSize/2, dotSize, dotSize);
        }
        // Bottom Center
        if(number >= 8) {
            this.drawPoint(g2, this.getWidth()/2-dotSize/2, this.getHeight()/3*2-dotSize/2, dotSize, dotSize);
        }
        // Bottom Right
        if(number >= 2) {
            this.drawPoint(g2, this.getWidth()/3*2-dotSize/2, this.getHeight()/3*2-dotSize/2, dotSize, dotSize);
        }
    }
    
    protected void drawPoint(Graphics g2, int x, int y, int width, int height) {
         g2.drawOval(x, y, width, height);
         g2.fillOval(x, y, width, height);
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        this.board.useDice();
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
        return String.format("Number: %d", this.number);
    }
}

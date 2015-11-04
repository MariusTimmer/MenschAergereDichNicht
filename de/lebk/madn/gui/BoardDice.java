package de.lebk.madn.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

public class BoardDice extends JComponent implements MouseListener {
    
    private int number = 9;
    private Color color = new Color(239, 239, 239);
    protected Board board;
    
    public BoardDice(Board board) {
        super();
        this.board = board;
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
        g2.setStroke(new BasicStroke(BoardElement.FIELD_BORDER_WIDTH));
        // Lets fill the dice
        g2.setColor(this.color);
        g2.fillRoundRect(BoardElement.CIRCLE_PADDING, BoardElement.CIRCLE_PADDING, this.getWidth() - (2 * BoardElement.CIRCLE_PADDING), this.getHeight() - (2 * BoardElement.CIRCLE_PADDING), BoardElement.CIRCLE_PADDING, BoardElement.CIRCLE_PADDING);
        // Draw line
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(BoardElement.CIRCLE_PADDING, BoardElement.CIRCLE_PADDING, this.getWidth() - (2 * BoardElement.CIRCLE_PADDING), this.getHeight() - (2 * BoardElement.CIRCLE_PADDING), BoardElement.CIRCLE_PADDING / 2, BoardElement.CIRCLE_PADDING / 2);
        // Write the number
        this.drawPoints(g2);
    }
    
    protected void drawPoints(Graphics g2) {
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

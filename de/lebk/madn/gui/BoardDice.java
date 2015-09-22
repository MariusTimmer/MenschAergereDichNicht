package de.lebk.madn.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class BoardDice extends JComponent {
    
    private int number = 1;
    
    public BoardDice() {
        super();
    }
    
    /**
     * Sets the number and updates the GUI
     * @param number 
     */
    public void setNumber(int number) {
        this.number = number;
        this.repaint();
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
        g2.setColor(new Color(239, 239, 239));
        g2.fillRoundRect(BoardElement.DEFAULT_CIRCLE_PADDING, BoardElement.DEFAULT_CIRCLE_PADDING, this.getWidth() - (2 * BoardElement.DEFAULT_CIRCLE_PADDING), this.getHeight() - (2 * BoardElement.DEFAULT_CIRCLE_PADDING), BoardElement.DEFAULT_CIRCLE_PADDING, BoardElement.DEFAULT_CIRCLE_PADDING);
        // Draw line
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(BoardElement.DEFAULT_CIRCLE_PADDING, BoardElement.DEFAULT_CIRCLE_PADDING, this.getWidth() - (2 * BoardElement.DEFAULT_CIRCLE_PADDING), this.getHeight() - (2 * BoardElement.DEFAULT_CIRCLE_PADDING), BoardElement.DEFAULT_CIRCLE_PADDING / 2, BoardElement.DEFAULT_CIRCLE_PADDING / 2);
        // Write the number
        g2.drawString(String.format("%d", this.number), (this.getWidth()/2)-3, (this.getHeight()/2) + 4);
    }
    
}

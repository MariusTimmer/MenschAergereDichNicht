package de.lebk.madn.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class BoardDice extends JComponent {
    
    private int number = 1;
    private JLabel label;
    
    public BoardDice() {
        super();
        this.setLayout(null);
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
        g2.setStroke(new BasicStroke(2));
        // Lets fill the dice
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(BoardElement.DEFAULT_CIRCLE_PADDING, BoardElement.DEFAULT_CIRCLE_PADDING, this.getWidth() - (2 * BoardElement.DEFAULT_CIRCLE_PADDING), this.getHeight() - (2 * BoardElement.DEFAULT_CIRCLE_PADDING), BoardElement.DEFAULT_CIRCLE_PADDING, BoardElement.DEFAULT_CIRCLE_PADDING);
        // Draw line
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(BoardElement.DEFAULT_CIRCLE_PADDING, BoardElement.DEFAULT_CIRCLE_PADDING, this.getWidth() - (2 * BoardElement.DEFAULT_CIRCLE_PADDING), this.getHeight() - (2 * BoardElement.DEFAULT_CIRCLE_PADDING), BoardElement.DEFAULT_CIRCLE_PADDING, BoardElement.DEFAULT_CIRCLE_PADDING);
        // Write the number
        this.label = new JLabel(String.format("Test %d", this.number), JLabel.CENTER);
        this.label.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.add(this.label);
    }
    
}

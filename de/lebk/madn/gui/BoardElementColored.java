package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import java.awt.Color;
import java.awt.Graphics;

public class BoardElementColored extends BoardElement {

    protected Color color;

    public BoardElementColored(Board board, Coordinate nextElement, Color color) {
        super(board, nextElement);
        this.color = color;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public boolean isSameColor(Color color) {
        return (this.color == color);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
    @Override
    public String toString() {
        return String.format("%s (Farbe: %s)", super.toString(), this.color.toString());
    }
    
}

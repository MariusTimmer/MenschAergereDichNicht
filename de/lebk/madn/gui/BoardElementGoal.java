package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import java.awt.Color;
import java.awt.Graphics;

public class BoardElementGoal extends BoardElementColored {

    public BoardElementGoal(Coordinate nextElement, Color color) {
        super(nextElement, color);
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int red, green, blue;
        Color lighter;
        float fraction = 0.5f;
        red     = (int) (this.color.getRed() * fraction);
        green   = (int) (this.color.getGreen() * fraction);
        blue    = (int) (this.color.getBlue() * fraction);
        lighter  = new Color(red, green, blue);
        this.drawField(g, lighter, this.color);
        this.drawOccupier(g);
    }
    
}

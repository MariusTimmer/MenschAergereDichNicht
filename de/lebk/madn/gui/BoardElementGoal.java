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
        int red, green, blue;
        Color lighter;
        float fraction = 0.5f;
        red     = (int) (this.color.getRed() * fraction);
        green   = (int) (this.color.getGreen() * fraction);
        blue    = (int) (this.color.getBlue() * fraction);
        lighter  = new Color(red, green, blue);
        this.drawField(g, lighter, this.color);
    }
    
}

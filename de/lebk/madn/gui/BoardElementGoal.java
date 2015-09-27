package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import java.awt.Color;
import java.awt.Graphics;

public class BoardElementGoal extends BoardElementColored {

    public BoardElementGoal(Coordinate nextElement, Color color) {
        super(nextElement, color);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int redB, greenB, blueB;
        int redF, greenF, blueF;
        Color border, inner;
        float fraction = 0.1f;
        redB     = (int) (this.color.getRed() * fraction);
        greenB   = (int) (this.color.getGreen() * fraction);
        blueB    = (int) (this.color.getBlue() * fraction);
        redF     = (int) (this.color.getRed() * (1-fraction));
        greenF   = (int) (this.color.getGreen() * (1-fraction));
        blueF    = (int) (this.color.getBlue() * (1-fraction));
        border   = new Color(redB, greenB, blueB);
        inner    = new Color(redF, greenF, blueF);
        this.drawField(g, border, inner);
        this.drawOccupier(g);
    }
    
}

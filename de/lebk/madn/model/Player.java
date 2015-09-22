package de.lebk.madn.model;

import java.awt.Color;

public class Player implements Comparable<Player> {

    private static final int DEFAULT_NUMBER_OF_FIGURES = 4;
    private Color color;
    private String name;
    private Figur[] figures;

    public Player(Color color) {
        this(color, new String());
    }

    public Player(Color color, String name) {
        this.color = color;
        this.name = name;
        this.initFigures(DEFAULT_NUMBER_OF_FIGURES);
    }

    private void initFigures(int nof) {
        this.figures = new Figur[nof];
        for (Figur figur : this.figures) {
            figur = new Figur(this.color);
        }
    }
    
    private int getPositionsOfAllFigures() {
        int amount = 0;
        for (Figur figur: this.figures) {
            amount += figur.getPosition();
        }
        return amount;
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", this.name, this.color.toString());
    }
    
    @Override
    public int compareTo(Player t) {
        return Integer.compare(this.getPositionsOfAllFigures(), t.getPositionsOfAllFigures());
    }

}

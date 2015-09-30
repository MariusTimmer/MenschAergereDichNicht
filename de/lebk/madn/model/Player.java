package de.lebk.madn.model;

import java.awt.Color;

public class Player implements Comparable<Player> {

    private static final int DEFAULT_NUMBER_OF_FIGURES = 4;
    private Color color;
    private String name;
    private Figur[] figures;
    private boolean hasFinished;

    public Player(Color color) {
        this(color, new String());
    }

    public Player(Color color, String name) {
        this.color = color;
        this.name = name;
        this.hasFinished = false;
        this.initFigures(DEFAULT_NUMBER_OF_FIGURES);
    }

    private void initFigures(int nof) {
        this.figures = new Figur[nof];
        for (int i = 0; i < nof; i++) {
            this.figures[i] = new Figur(this.color);
        }
    }
    
    public void finished() {
        this.hasFinished = true;
    }
    
    public boolean hasFinished() {
        return this.hasFinished;
    }
    
    private int getPositionsOfAllFigures() {
        int amount = 0;
        for (Figur figur: this.figures) {
            amount += figur.getPosition();
        }
        return amount;
    }
    
    public Figur[] getFigures() {
        return this.figures;
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

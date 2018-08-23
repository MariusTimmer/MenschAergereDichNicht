package de.lebk.madn.model;

import java.awt.Color;

public class Figur implements Comparable<Figur> {

    public static final int POSITION_START = 0;
    private Color color;
    private int position;

    public Figur(Color color) {
        this.color = color;
        this.position = POSITION_START;
    }
    
    public boolean isAtHome() {
        return (this.position == POSITION_START);
    }

    public Color getColor() {
        return this.color;
    }

    public int getPosition() {
        return this.position;
    }

    public void addSteps(int steps) {
        this.position += steps;
    }

    @Override
    public String toString() {
        return String.format("Figur (%s)", this.getColor().toString());
    }

    public boolean equals(Figur other) {
        return ((this.color == other.color) && (this.position == other.position));
    }

    @Override
    public int compareTo(Figur other) {
        return Integer.compare(this.position, other.position);
    }

}

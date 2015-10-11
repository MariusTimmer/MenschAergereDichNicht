package de.lebk.madn.model;

import java.awt.Color;

public class Figur implements Comparable<Figur> {

    private Color color;
    private int position;

    public Figur(Color color) {
        this.color = color;
        this.position = 0;
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

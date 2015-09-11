package de.timmer.menschaergeredichnicht.model;

public class Figure {

    public static int POSITION_HOME = -1;
    private int relative_position;
    
    public Figure() {
        this.relative_position = POSITION_HOME;
    }

    public void increase() {
        this.increase(1);
    }

    public void increase(int number_of_steps) {
        this.relative_position += number_of_steps;
    }
    
    public boolean isAtHome() {
        return (this.relative_position == POSITION_HOME);
    }
    
    public String toString() {
        return String.format("Position: %d", this.relative_position);
    }

}
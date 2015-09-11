package de.timmer.menschaergeredichnicht.model;

import java.awt.Color;

public class Player {

    public static final int DEFAULT_NUMBER_OF_FIGURES = 4;
    private Color color;
    private Figure[] figures;
    
    public Player(Color color) {
        this.color = color;
        this.initFigures(DEFAULT_NUMBER_OF_FIGURES);
    }
    
    private void initFigures(int number_of_figures) {
        int i;
        this.figures = new Figure[number_of_figures];
        for (i = 0; i < number_of_figures; i++) {
            this.figures[i] = new Figure();
        }
    }
    
    public Color getColor() {
        return this.color;
    }

}
package de.timmer.menschaergeredichnicht.gui;

import javax.swing.JFrame;

public class GameBoard extends JFrame {

    public static final int DEFAULT_BOARD_SIZE = 11;
    private Field[][] fields;
    private int map_size;
    
    public GameBoard() {
        this(DEFAULT_BOARD_SIZE);
    }
    
    public GameBoard(int board_size) {
        super();
        this.initBoard(board_size);
    }
    
    private void initBoard(int size) {
        int x, y;
        this.map_size = size;
        this.fields = new Field[this.map_size][];
        for (y = 0; y < this.map_size; y++) {
            this.fields[y] = new Field[this.map_size];
            for (x = 0; x < this.map_size; x++) {
                this.fields[y][x] = new Field();
            }
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(600, 400);
        this.setVisible(true);
    }

}
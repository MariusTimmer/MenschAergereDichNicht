package de.timmer.menschaergeredichnicht.gui;

import javax.swing.JFrame;

public class GameBoard extends JFrame {

    public static final int DEFAULT_BOARD_SIZE = 11;
    private BoardElement[][] fields;
    private int map_size;
    
    public GameBoard() {
        this(DEFAULT_BOARD_SIZE);
    }
    
    public GameBoard(int board_size) {
        super();
        this.initBoard(board_size);
    }

    public BoardElement getBoardElement(int x, int y) {
        return this.fields[y][x];
    }
    
    public BoardElement getBoardElement(Coordinate c) {
        return this.getBoardElement(c.getX(), c.getY());
    }
    
    private void initBoard(int size) {
        int x, y;
        this.map_size = size;
        this.fields = new BoardElement[this.map_size][];
        for (y = 0; y < this.map_size; y++) {
            this.fields[y] = new BoardElement[this.map_size];
            for (x = 0; x < this.map_size; x++) {
                // @todo: Read the coordinate for the next element and put it into the constructor
                Coordinate next = new Coordinate(0,0);
                this.fields[y][x] = new Field(next);
            }
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(600, 400);
        this.setVisible(true);
    }

}
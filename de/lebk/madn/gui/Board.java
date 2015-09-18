package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import de.lebk.madn.Logger;
import java.awt.Color;
import javax.swing.JFrame;

public class Board extends JFrame {
    
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;
    private BoardElement[][] fields;

    public Board(String mapfile) {
        super("Mensch ärgere dich nicht");
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.initFields(mapfile);
        this.setVisible(true);
    }
    
    private void initFields(String mapfile) {
        MapLoader loader = new MapLoader(mapfile);
        String[] lines = loader.readFile();
        int size =  loader.getMapSize(lines);
        this.fields = new BoardElement[size][size];
        for (String line: lines) {
            BoardElement.BOARD_ELEMENT_TYPES typ = loader.getTypeFromLine(line);
            Coordinate objPos = loader.getCoordinateFromLine(line);
            int y = objPos.getY();
            int x = objPos.getX();
            Coordinate next = loader.getCoordinateForNextElementFromLine(line);
            Color color;
            switch (typ) {
                case HOME:
                    color = loader.getColorFromLine(line);
                    this.fields[y][x] = new BoardElementHome(next, color);
                    break;
                case WAYPOINT:
                    this.fields[y][x] = new BoardElementWaypoint(next);
                    break;
                case GOAL:
                    this.fields[y][x] = new BoardElementGoal(next);
                    break;
                default:
                    Logger.write(String.format("Board::initFields: Der Typ \"%s\" wurde noch nicht implementiert!", typ.toString()));
            }
        }
    }
    
}

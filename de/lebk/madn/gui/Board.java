package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import de.lebk.madn.Logger;
import java.awt.Color;
import javax.swing.JFrame;

public class Board extends JFrame {
    
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 800;
    public static final int DEFAULT_BORDER = 48;
    private BoardElement[][] fields;

    public Board(String mapfile) {
        super("Mensch ärgere dich nicht");
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
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
            Logger.write(String.format("Erzeuge Feld vom Typ %s", typ.toString()));
            int elem_width = ((this.getWidth() - DEFAULT_BORDER) / size);
            int elem_left = elem_width * x;
            int elem_top = elem_width * y;
            this.fields[x][y].setBounds(elem_left, elem_top, elem_width, elem_width);
            switch (typ) {
                case HOME:
                    color = loader.getColorFromLine(line);
                    this.fields[x][y] = new BoardElementHome(next, color);
                    break;
                case WAYPOINT:
                    this.fields[x][y] = new BoardElementWaypoint(next);
                    break;
                case GOAL:
                    color = loader.getColorFromLine(line);
                    this.fields[x][y] = new BoardElementGoal(next, color);
                    break;
                default:
                    Logger.write(String.format("Board::initFields: Der Typ \"%s\" wurde noch nicht implementiert!", typ.toString()));
            }
            this.add(this.fields[x][y]);
        }
    }
    
}

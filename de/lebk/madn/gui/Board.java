package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import de.lebk.madn.Logger;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Board extends JFrame {
    
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 800;
    public static final int DEFAULT_BORDER = 48;
    private static final String DEFAULT_DIALOGBOX_TITLE = "Information";
    private BoardElement[][] fields;
    private BoardDice dice;

    public Board(String mapfile) {
        super("Mensch ärgere dich nicht");
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT + DEFAULT_BORDER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.initFields(mapfile);
        this.setVisible(true);
    }
    
    /**
     * Sets the given number to the Board
     * @param number Diced number
     */
    public void setDice(int number) {
        this.dice.setNumber(number);
    }
    
    private void initFields(String mapfile) {
        MapLoader loader = new MapLoader(mapfile);
        String[] lines = loader.readFile();
        if (lines != null) {
            int size = loader.getMapSize(lines);
            int elem_width = ((this.getWidth()) / size);
            this.fields = new BoardElement[size][size];
            for (String line: lines) {
                BoardElement.BOARD_ELEMENT_TYPES typ = loader.getTypeFromLine(line);
                Coordinate objPos = loader.getCoordinateFromLine(line);
                int y = objPos.getY();
                int x = objPos.getX();
                Coordinate next = loader.getCoordinateForNextElementFromLine(line);
                Color color;
                int elem_left = elem_width * x;
                int elem_top = elem_width * y;
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
                this.fields[x][y].setBounds(elem_left, elem_top, elem_width, elem_width);
                this.add(this.fields[x][y]);
            }
            // Add the dice
            this.dice = new BoardDice();
            this.dice.setBounds(elem_width * 5, elem_width * 5, elem_width, elem_width);
            this.add(this.dice);
        } else {
            // Error reading file
            this.showDialogBox(String.format("Mapfile \"%s\" konnte nicht gelesen werden!", mapfile));
        }
    }
    
    public void showDialogBox(String text) {
        this.showDialogBox(DEFAULT_DIALOGBOX_TITLE, text);
    }
    
    public void showDialogBox(String title, String text) {
        JOptionPane optionPane = new JOptionPane(text);
        JDialog dialog = new JDialog(this, title, true);
        dialog.setContentPane(optionPane);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        dialog.setVisible(true);
    }
    
}

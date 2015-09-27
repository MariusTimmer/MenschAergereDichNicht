package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import de.lebk.madn.Logger;
import de.lebk.madn.MapException;
import de.lebk.madn.MapNoSpaceForDiceException;
import de.lebk.madn.MapNotFoundException;
import de.lebk.madn.MapNotParsableException;
import de.lebk.madn.MenschAergereDichNichtException;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JFrame;

public class Board extends JFrame {
    
    public static final int		DEFAULT_SIZE	= 800;
    public static final int		DEFAULT_BORDER	= 48;
    private ArrayList<BoardElementHome>	homes;
    private ArrayList<BoardElementGoal>	goals;
    private BoardElement[][]		fields;
    private BoardDice			dice;

    public Board(String mapfile) throws MenschAergereDichNichtException {
        super("Mensch ärgere dich nicht");
        this.setSize(DEFAULT_SIZE, DEFAULT_SIZE + DEFAULT_BORDER);
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
    
    public BoardElementHome[] getHomesOf(Color color) {
        LinkedList<BoardElementHome> homes = new LinkedList<>();
        for (BoardElementHome home: this.homes) {
            if (home.isSameColor(color)) {
                homes.add(home);
            }
        }
        return homes.toArray(new BoardElementHome[homes.size()]);
    }
    
    public BoardElementGoal[] getGoalsOf(Color color) {
        LinkedList<BoardElementGoal> goals = new LinkedList<>();
        for (BoardElementGoal goal: this.goals) {
            if (goal.isSameColor(color)) {
                goals.add(goal);
            }
        }
        return goals.toArray(new BoardElementGoal[goals.size()]);
    }
    
    public int getMapSize() {
        return this.fields.length;
    }
    
    private void initFields(String mapfile) throws MapException {
        MapLoader loader = new MapLoader(mapfile);
        this.homes = new ArrayList<>();
        this.goals = new ArrayList<>();
        String[] lines = loader.readFile();
        if (lines != null) {
            int size = loader.getMapSize(lines);
            this.fields = new BoardElement[size][size];
            for (String line: lines) {
                BoardElement.BOARD_ELEMENT_TYPES typ = loader.getTypeFromLine(line);
                Coordinate objPos = loader.getCoordinateFromLine(line);
                int y = objPos.getY();
                int x = objPos.getX();
                Coordinate next = loader.getCoordinateForNextElementFromLine(line);
                Color color;
                BoardElement element;
                switch (typ) {
                    case HOME:
                        color = loader.getColorFromLine(line);
                        this.homes.add(new BoardElementHome(next, color));
                        element = (BoardElement) this.homes.get(this.homes.size()-1);
                        break;
                    case WAYPOINT:
                        element = new BoardElementWaypoint(next);
                        break;
                    case GOAL:
                        color = loader.getColorFromLine(line);
                        this.goals.add(new BoardElementGoal(next, color));
                        element = (BoardElement) this.goals.get(this.goals.size()-1);
                        break;
                    default:
                        throw new MapNotParsableException(String.format("BoardElement \"%s\" is not implemented yet / unknown!", typ.toString()));
                }
                this.fields[x][y] = element;
                this.add(this.fields[x][y]);
            }
            // Add the dice
            this.dice = new BoardDice();
            this.add(this.dice);
            repositioningElements();
        } else {
            // Error reading file
            throw new MapNotFoundException("No lines found in mapfile");
        }
    }
    
    private boolean isPositionAvailable(Coordinate c) {
        return (this.fields[c.getX()][c.getY()] == null);
    }
    
    private Coordinate calcDicePos() {
        Coordinate c = new Coordinate(Math.round(this.getMapSize()/2), Math.round(this.getMapSize()/2));
        if (this.isPositionAvailable(c)) {
            // The center of the board would be available for the dice
            return c;
        }
        for (int y = (this.getMapSize() - 1); y >= 0; y--) {
            c.setY(y);
            for (int x = (this.getMapSize() - 1); x >= 0; x--) {
                c.setX(x);
                if (this.isPositionAvailable(c)) {
                    return c;
                }
            }
        }
        // There is no available place for the dice
        return null;
    }
    
    private void repositioningElements() {
        int x, y;
        int elem_width = (Math.min(this.getWidth(), (this.getHeight() - DEFAULT_BORDER)) / this.getMapSize());
        BoardElement.CIRCLE_PADDING = Math.round(elem_width / 8);
        for (y = 0; y < this.getMapSize(); y++) {
            for (x = 0; x < this.getMapSize(); x++) {
                int elem_left = elem_width * x;
                int elem_top = elem_width * y;
                if (this.fields[x][y] != null) {
                    // Just for elements
                    this.fields[x][y].setBounds(elem_left, elem_top, elem_width, elem_width);
                }
            }
        }
        Coordinate dicePos = this.calcDicePos();
        if (dicePos != null) {
            this.dice.setBounds((elem_width * dicePos.getX()) + (elem_width/8), (elem_width * dicePos.getY()) + (elem_width/8), (elem_width/4)*3, (elem_width/4)*3);
        } else {
            // No diceposition
            Logger.write(String.format("There is no space left to draw the dice! Try to use a better mapfile!"));
        }
    }

    @Override
    public void validate() {
        super.validate();
        this.repositioningElements();
    }
    
}

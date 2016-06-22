package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import de.lebk.madn.Logger;
import de.lebk.madn.MADNControlInterface;
import de.lebk.madn.MapException;
import de.lebk.madn.MapNoSpaceForDiceException;
import de.lebk.madn.MapNotFoundException;
import de.lebk.madn.MapNotParsableException;
import de.lebk.madn.MenschAergereDichNichtException;
import de.lebk.madn.model.Figur;
import de.lebk.madn.model.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JFrame;

/** 
 * The GUI that represents the board of the game and with which the player(s) will
 * interact during the game if they are not using the network-interface.
 * @author Marius Timmer
 */

public class Board extends JFrame implements KeyListener {
    
    public static final int             DEFAULT_SIZE	= 800;
    public static final int             DEFAULT_BORDER	= 48;
    public static final char            DEFAULT_DICE_KEY = 'w';
    protected char                      dice_key;
    private ArrayList<BoardElementHome> homes;
    private ArrayList<BoardElementGoal> goals;
    private BoardElement[][]            fields;
    private BoardDice                   dice;
    private MADNControlInterface        game;

    /**
     * Generates the board-gui
     * @param game The gamecontroler
     * @param mapfile Path to the mapfile to load
     * @throws MenschAergereDichNichtException For the case an error occours
     */
    public Board(MADNControlInterface game, String mapfile) throws MenschAergereDichNichtException {
        super("Mensch ärgere dich nicht");
        this.game = game;
        this.setSize(this.getScreenHeight() - DEFAULT_BORDER, this.getScreenHeight());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.initFields(mapfile);
        this.setVisible(true);
        this.dice_key = DEFAULT_DICE_KEY;
        this.addKeyListener(this);
    }

    /**
     * Detects the screen-resolution of the current monitor
     * @return pixels
     */
    public int getScreenHeight() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        return gd.getDisplayMode().getHeight();
    }
    
    /**
     * Sets the given number to the Board
     * @param number Diced number
     */
    public void setDice(int number, Color color) {
        this.dice.setNumber(number);
        this.dice.setColor(color);
    }

    /** 
     * Finds the home-fields for a special color/player
     * @param color Color of the player which owns the wanted homes
     * @return All home-fields for the wanted user/color
     */
    public BoardElementHome[] getHomesOf(Color color) {
		int i, k = 0;
		BoardElementHome[] player_homes = new BoardElementHome[4];
		for (i = 0; i < this.homes.size(); i++) {
			if (this.homes.get(i).isSameColor(color)) {
				player_homes[k] = this.homes.get(i);
				k++;
			}
		}
	    return player_homes;
    }

    /**
     * Searchs the field which is occupied by the given figure
     * @param figure Figure which field should be searched
     * @return The field or null if no field was found
     */
    public BoardElement getFieldOfFigure(Figur figure) {
        int x, y;
        for (y = 0; y < this.fields.length; y++) {
            for (x = 0; x < this.fields[y].length; x++) {
                BoardElement current_field = this.fields[y][x];
                if (current_field != null) {
                    /**
                     * We have to be sure that the current field we are
                     * checking does exists to prevent a NullPointerException.
                     */
                    Figur current_figure = current_field.getOccupier();
                    if ((current_figure != null) && (current_figure.equals(figure))) {
                        /**
                         * Additionally we have to check if the occupier is set and
                         * if so if it is the wanted figure. If we have found the
                         * field of the wanted figure, we can return the current field.
                         */
                        return current_field;
                    }
                }
            }
        }
        return null;
    }

    /** 
     * Finds the goal-fields for a special color/player
     * @param color Color of the player which owns the wanted goals
     * @return All goal-fields for the wanted user/color
     */
    public BoardElementGoal[] getGoalsOf(Color color) {
		int i, k = 0;
	    BoardElementGoal[] player_goals = new BoardElementGoal[4];
	    for (i = 0; i < this.goals.size(); i++) {
		    if (this.goals.get(i).isSameColor(color)) {
				player_goals[k] = this.goals.get(i);
				k++;
			}
		}
		return player_goals;
    }

    public boolean moveFigureAtHome(Figur figur) {
	    BoardElementHome[] player_homes = this.getHomesOf(figur.getColor());
	    for (int i = 0; i < player_homes.length; i++) {
		    if (!player_homes[i].isOccupied()) {
			    player_homes[i].occupie(figur);
			    return true;
		    }
	    }
	    return false;
    }

    /**
     * Determinates the size of this map (One side).
     * @return Size of a side of the map
     */
    public int getMapSize() {
        return this.fields.length;
    }

    /**
     * Initiates the fields on the board (read by file)
     * @param mapfile The path to the mapfile which should be loaded
     * @throws MapException if an error occours
     */
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
                        this.homes.add(new BoardElementHome(this, objPos, next, color));
                        element = (BoardElement) this.homes.get(this.homes.size()-1);
                        break;
                    case WAYPOINT:
			if (loader.hasAlternative(line)) {
				// Alternative way found
				Coordinate alternative = loader.getCoordinateForAlternative(line);
				element = new BoardElementWaypoint(this, objPos, next, alternative);
			} else {
				// There is no alternativ way
				element = new BoardElementWaypoint(this, objPos, next);
			}
                        break;
                    case GOAL:
                        color = loader.getColorFromLine(line);
                        this.goals.add(new BoardElementGoal(this, objPos, next, color));
                        element = (BoardElement) this.goals.get(this.goals.size()-1);
                        break;
                    default:
                        throw new MapNotParsableException(String.format("BoardElement \"%s\" is not implemented yet / unknown!", typ.toString()));
                }
                this.fields[x][y] = element;
                this.add(this.fields[x][y]);
            }
            // Add the dice
            this.dice = new BoardDice(this);
            this.add(this.dice);
            repositioningElements();
        } else {
            // Error reading file
            throw new MapNotFoundException("No lines found in mapfile");
        }
    }
    
    /**
     * Checks if the given position is available
     * @param c Coordinate which should be checked
     * @return True if the coordinate is available or false
     */
    private boolean isPositionAvailable(Coordinate c) {
        return (this.fields[c.getX()][c.getY()] == null);
    }
    
    /**
     * Calculate the position whre the dice shuld be positioned
     */
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
    
    /**
     * Resizes the elements in the frame and sets new positions
     */
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

    /**
     * Resized the elements in the frame if it is resized
     */
    @Override
    public void validate() {
        super.validate();
        this.repositioningElements();
    }
    
    /** 
     * Calls the moveFigur-method in the gamecontroller
     * @param position The coordinate from which the figure should be moved
     * @param figur The figure which should be moved
     */
    public void moveFigur(Coordinate position, Figur figur) {
        this.game.moveFigur(position, figur, this.dice.getNumber());
    }
    
    /**
     * Calls the dice-method in the gamecontroller
     */
    public void useDice() {
        this.game.userDice();
    }
	
    /**
     * Just calls getBoardElement(int x, int y)
     * @param c Coordinates
     * @return Null if the coordinates are invalid or there is no element or the BoardElement
     */
    public BoardElement getBoardElement(Coordinate c) {
        return this.getBoardElement(c.getX(), c.getY());
    }
	
    /**
     * Returns the BoardElement which is placed at (x/y)
     * @param x X-Coordinate
     * @param y Y-Coordinate
     * @return Null if the coordinates are invalid or there is no element or the BoardElement
     */
    public BoardElement getBoardElement(int x, int y) {
	if ((x < 0) || (y < 0) || (x >= this.fields.length) || (y >= this.fields[x].length)) {
		// Coordinates are not valid
		return null;
	}
	return this.fields[x][y];
    }
    
    public boolean isAlternativeAvailable(Coordinate current_coordinate, int player)
    {
	BoardElement current_field = this.getBoardElement(current_coordinate);
	if ((current_field instanceof BoardElementWaypoint) && (((BoardElementWaypoint)current_field).hasAlternative())) {
            BoardElementWaypoint wp = (BoardElementWaypoint) current_field;
            Coordinate alternative_coordinate = wp.getAlternative();
            if (alternative_coordinate != null) {
                Color fieldcolor  = ((BoardElementColored)this.getBoardElement(alternative_coordinate)).getColor();
                Color playercolor = this.game.getPlayer(player).getColor();
                return (this.sameColor(fieldcolor, playercolor));
            } else {
                Logger.write(String.format("The mapfile is corrupt! The alternative point of %s is null!", current_coordinate));
                return false;
            }
	} else {
            return false;
	}
    }

    /**
     * Checks if color a and color b are the same colors
     * @param a Color to compare color B to
     * @param b Color to compare color A to
     * @return True if the colors  have the same color-value or false
     */
    public boolean sameColor(Color a, Color b)
    {
        return ((a.getRed() == b.getRed()) &&
                (a.getGreen() == b.getGreen()) &&
                (a.getBlue() == b.getBlue()));
    }
    
    /**
     * Will be called when a key is typed
     * @param e The event which called this method
     */
    public void keyTyped(KeyEvent e)
    {
    }

    /**
     * Will be called when a key is pressed. It is able to tell
     * the game to throw the dice.
     * @param e The event which called this method
     */
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyChar() == this.dice_key)
        {
            this.game.userDice();
        }
    }
    
    /**
     * Will be called when a key will be released
     * @param e The event which called this method
     */
    public void keyReleased(KeyEvent e)
    {
    }
    
}

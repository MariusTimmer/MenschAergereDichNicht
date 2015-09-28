package de.lebk.madn;

/**
 * Class that runs the game and handles all I/O
 * @author Marius Timmer <admin@MariusTimmer.de>
 */

import de.lebk.madn.gui.Board;
import de.lebk.madn.gui.BoardElementHome;
import de.lebk.madn.model.Figur;
import de.lebk.madn.model.Player;
import java.awt.Color;

public class Spiel {

    public static final Color[] AVAILABLE_COLORS = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.PINK, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.GRAY};  // Array with all available colors
    public static final int DEFAULT_DICE_MAXIMUM = 6;  // Default maximum number the dice can bring
    private int dice_maximum = DEFAULT_DICE_MAXIMUM;   // This is the maximal number for the current dice
    private Player[] players;  // Array that stores all players
    private Board board;       // GUI
    private long inittime;     // Timestamp of initialisation
    private long starttime;    // Timestamp of starttime

    /**
     * Creates a game
     * @param number_of_players The game will be created for this number of players
     * @param mapfile Filename of the mapfile
     */
    public Spiel(int number_of_players, String mapfile) {
        this.inittime = System.currentTimeMillis();
        this.initGUI(mapfile);
        this.initPlayers(number_of_players);
        this.starttime = System.currentTimeMillis();
        Logger.write(String.format("Ladezeit: %d ms", (this.starttime - this.inittime)));
    }
    
    private void initGUI(String mapfile) {
        // Init the board
        try {
            this.board = new Board(mapfile);
        } catch (MenschAergereDichNichtException e) {
            Logger.write(String.format("Error: %s", e.getMessage()));
        }
    }

    private void initPlayers(int number_of_players) {
        int i;
        // Create the player-array
        this.players = new Player[number_of_players];
        for (i = 0; i < number_of_players; i++) {
            // Erzeuge die Spieler und vergib ihnen die Farben
            Color playercolor = AVAILABLE_COLORS[i % AVAILABLE_COLORS.length];
            this.players[i] = new Player(playercolor);
            for (Figur figur: this.players[i].getFigures()) {
                boolean figurSet = false;
                for (BoardElementHome home: this.board.getHomesOf(playercolor)) {
                    if ((!home.isOccupied()) && (figurSet == false)) {
                        home.occupie(figur);
                        figurSet = true;
                    }
                }
            }
        }
    }
    
    /**
     * Let the dice fall
     * @return A random number
     */
    private int dice() {
        return (int) (Math.random() * (this.dice_maximum - 1)) + 1;
    }
    
    /**
     * Creates a String that contains the current game-state
     * @return State containing string
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(String.format("Spiel mit %d Spielern", this.players.length));
        for (int i = 0; i < this.players.length; i++) {
            if (i > 0) {
                output.append(", ");
            }
            output.append(this.players[i].toString());
        }
        return output.toString();
    }

}

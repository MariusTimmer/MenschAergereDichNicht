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

    public static final Color[]  AVAILABLE_COLORS        = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.PINK, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.GRAY};  // Array with all available colors
    public static final int      DEFAULT_DICE_MAXIMUM    = 6;                     // Default maximum number the dice can bring
    private int                  dice_maximum            = DEFAULT_DICE_MAXIMUM;  // This is the maximal number for the current dice
    private Player[]             players;                                         // Array that stores all players
    private Board                board;                                           // GUI
    private int                  activePlayer            = -1;                    // Index for the current player
    private long                 inittime;                                        // Timestamp of initialisation
    private long                 starttime;                                       // Timestamp of starttime
    private int                  last_diced_number;

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
        if (!this.switchToNextPlayer()) {
            Logger.write(String.format("Unknown error while switching to the next player"));
        }
    }
    
    private boolean initGUI(String mapfile) {
        // Init the board
        try {
            this.board = new Board(mapfile);
            return true;
        } catch (MenschAergereDichNichtException e) {
            Logger.write(String.format("Error: %s", e.getMessage()));
            return false;
        }
    }
    
    /**
     * Checks if the game is over or if there are still running figures in the game
     * @return True if the game is over
     */
    public boolean isGameOver() {
        int runningPlayers = 0;
        for (Player player: this.players) {
            if (!player.hasFinished()) {
                runningPlayers++;
            }
        }
        return (runningPlayers <= 1);
    }

    /**
     * Returns the current player
     * @return Current player
     */
    public Player getCurrentPlayer() {
        return this.players[this.activePlayer];
    }

    /**
     * Switches to the next player
     * @return True if a player is selected or false
     */
    public boolean switchToNextPlayer() {
        int checked = 0;
        int index;
        while ((checked < this.players.length)) {
            checked++;
            index = (this.activePlayer + checked) % this.players.length;
            if (!this.players[index].hasFinished()) {
                this.activePlayer = index;
                this.dice();
                return true;
            }
        }
        return false;
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
        this.last_diced_number = (int) (Math.random() * (this.dice_maximum - 1)) + 1;
        this.board.setDice(this.last_diced_number, this.getCurrentPlayer().getColor());
        return this.last_diced_number;
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

package de.lebk.madn;

import de.lebk.madn.gui.Board;
import de.lebk.madn.Logger;
import de.lebk.madn.model.Player;
import java.awt.Color;

public class Spiel {

    public static final Color[] AVAILABLE_COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    public static final int DEFAULT_DICE_MAXIMUM = 6;
    private int dice_maximum = DEFAULT_DICE_MAXIMUM;
    private Player[] players;
    private Board board;
    private long inittime;
    private long starttime;

    public Spiel(int number_of_players, String mapfile) {
        this.inittime = System.currentTimeMillis();
        this.initPlayers(number_of_players);
        this.initGUI(mapfile);
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
        }
    }
    
    /**
     * Let the dice fall
     * @return A random number
     */
    private int dice() {
        return (int) (Math.random() * (this.dice_maximum - 1)) + 1;
    }
    
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

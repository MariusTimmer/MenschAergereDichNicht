package de.lebk.madn;

import de.lebk.madn.gui.Board;
import java.awt.Color;
import de.lebk.madn.model.Player;

public class Spiel {

    public static final Color[] AVAILABLE_COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    public static final int DEFAULT_DICE_MAXIMUM = 6;
    private int dice_maximum = DEFAULT_DICE_MAXIMUM;
    private Player[] players;
    private Board board;

    public Spiel(int number_of_players, String mapfile) {
        this.initPlayers(number_of_players);
        this.initGUI(mapfile);
    }
    
    private void initGUI(String mapfile) {
        // Init the board
        this.board = new Board(mapfile);
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

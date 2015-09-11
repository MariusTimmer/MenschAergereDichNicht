package de.timmer.menschaergeredichnicht;

import java.awt.Color;
import de.timmer.menschaergeredichnicht.model.Player;
import de.timmer.menschaergeredichnicht.gui.GameBoard;

class Controller {

    public static final Color[] AVAILABLE_PLAYER_COLORS = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
    private Player[] players;
    private GameBoard board;
    
    public Controller(int number_of_players) {
        this.initPlayers(number_of_players);
        this.board = new GameBoard(11);
    }
    
    /**
     * Initialize the player-array
     * @param number_of_players Number of players to be created
     */
    private void initPlayers(int number_of_players) {
        int i;
        this.players = new Player[number_of_players];
        for (i = 0; i < number_of_players; i++) {
            this.players[i] = new Player(AVAILABLE_PLAYER_COLORS[i]);
        }
    }
    
    /**
     * Dice a number between 1 and 6
     * @return diced number
     */
    int dice() {
        return (int) (Math.round(Math.random() * 6) + 1);
    }

}
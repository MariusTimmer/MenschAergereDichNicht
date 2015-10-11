package de.lebk.madn;

/**
 * Class that runs the game and handles all I/O
 * @author Marius Timmer <admin@MariusTimmer.de>
 */

import de.lebk.madn.gui.Board;
import de.lebk.madn.gui.BoardElement;
import de.lebk.madn.gui.BoardElementHome;
import de.lebk.madn.model.Figur;
import de.lebk.madn.model.Player;
import java.awt.Color;

public class Spiel implements MADNControlInterface {

    public static final Color[]  AVAILABLE_COLORS        = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.PINK, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.GRAY};  // Array with all available colors
    public static final int      DEFAULT_DICE_MAXIMUM    = 6;                     // Default maximum number the dice can bring
    private int                  dice_maximum            = DEFAULT_DICE_MAXIMUM;  // This is the maximal number for the current dice
    private Player[]             players;                                         // Array that stores all players
    private Board                board;                                           // GUI
    private int                  activePlayer            = -1;                    // Index for the current player
    private long                 inittime;                                        // Timestamp of initialisation
    private long                 starttime;                                       // Timestamp of starttime
    private int                  last_diced_number;
    private boolean              waitingForInteraction   = false;

    /**
     * Creates a game
     * @param number_of_players The game will be created for this number of players
     * @param mapfile Filename of the mapfile
     */
    public Spiel(int number_of_players, String mapfile) {
        this.inittime = System.currentTimeMillis();
        this.initGUI(this, mapfile);
        this.initPlayers(number_of_players);
        this.starttime = System.currentTimeMillis();
        Logger.write(String.format("Ladezeit: %d ms", (this.starttime - this.inittime)));
        if (!this.switchToNextPlayer()) {
            Logger.write(String.format("Unknown error while switching to the next player"));
        }
    }
    
    private boolean initGUI(MADNControlInterface game, String mapfile) {
        // Init the board
        try {
            this.board = new Board(game, mapfile);
            return true;
        } catch (MenschAergereDichNichtException e) {
            Logger.write(String.format("Error: %s", e.getMessage()));
            return false;
        }
    }

    public boolean IsWaitingForInteraction() {
        return this.waitingForInteraction;
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
		this.waitingForInteraction = true;
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

	protected BoardElement calculateBoardElement(BoardElement source, int steps) {
		int i;
		Coordinate nextPosition = source.getNextElementPosition();
		for (i = 1; i <= steps; i++) {
			if (nextPosition == null) {
				// To many steps / not enough fields
				return null;
			}
			source = this.board.getBoardElement(nextPosition);
			nextPosition = source.getNextElementPosition();
		}
		return source;
	}

    @Override
    public boolean moveFigur(Coordinate position, Figur figur, int steps) {
        if (this.waitingForInteraction) {
            int i, k;
            Figur target = null;
            for (i = 0; i < this.players.length; i++) {
                for (k = 0; k < this.players[i].getFigures().length; k++)  {
                    if (figur.equals(this.players[i].getFigures()[k])) {
                        // Found the correct figure
                        target = this.players[i].getFigures()[k];
                    }
                }
            }
            if (target != null) {
				BoardElement currentField, targetField;
				currentField = this.board.getBoardElement(position);
				targetField = this.calculateBoardElement(currentField, steps);
				if (targetField.isOccupied()) {
					Logger.write(this, String.format("Target is occupied already!"));
					return false;
				}
                target.addSteps(steps);
				targetField.occupie(target);
				currentField.occupie(null);
				this.waitingForInteraction = false;
                Logger.write(String.format("Figur (%s) moved %d fields", target, steps));
                return true;
            }
        } else {
            // Not waiting for interaction
            Logger.write(String.format("Can not move figure %s, not waiting for interaction", figur));
        }
        return false;
    }

}

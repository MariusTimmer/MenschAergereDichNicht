package de.lebk.madn;

/**
 * Class that runs the game and handles all I/O
 * @author Marius Timmer <admin@MariusTimmer.de>
 */

import de.lebk.madn.gui.Board;
import de.lebk.madn.gui.BoardElement;
import de.lebk.madn.gui.BoardElementHome;
import de.lebk.madn.gui.BoardElementWaypoint;
import de.lebk.madn.model.Figur;
import de.lebk.madn.model.Player;
import java.awt.Color;
import java.awt.HeadlessException;

public class Spiel extends ActionManager implements MADNControlInterface {

    public static final Color[]  AVAILABLE_COLORS        = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.PINK, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.GRAY};  // Array with all available colors
    public static final int      DEFAULT_DICE_MAXIMUM    = 6;                     // Default maximum number the dice can bring
    private int                  dice_maximum            = DEFAULT_DICE_MAXIMUM;  // This is the maximal number for the current dice
    private Player[]             players;                                         // Array that stores all players
    private Board                board;                                           // GUI
    private int                  activePlayer            = -1;                    // Index for the current player
    private long                 inittime;                                        // Timestamp of initialisation
    private long                 starttime;                                       // Timestamp of starttime
    private int                  last_diced_number;
    private int                  round                   = 0;
    private int                  thrown_dices_this_round;

    /**
     * Creates a game
     * @param number_of_players The game will be created for this number of players
     * @param mapfile Filename of the mapfile
     */
    public Spiel(int number_of_players, String mapfile) {
        this.inittime = System.currentTimeMillis();
        try {
            this.initGUI(this, mapfile);
        } catch (HeadlessException e) {
            Logger.write("There is no display-variable set! Make sure your X-Server is running.");
            System.exit(-1);
        }
        this.initPlayers(number_of_players);
        this.board.setDice(0, this.players[0].getColor());
        this.starttime = System.currentTimeMillis();
        Logger.write(String.format("Loadingtime: %d ms", (this.starttime - this.inittime)));
        this.postFigurMove();
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
        int lastIndex = this.activePlayer;
        int index;
        while ((checked < this.players.length)) {
            checked++;
            index = (this.activePlayer + checked) % this.players.length;
            if (!this.players[index].hasFinished()) {
                this.activePlayer = index;
                this.setCurrentAction(AVAILABLE_ACTIONS.DICE);
                if (lastIndex > index) {
                    this.onNewRound();
                }
                this.onUserChanged();
                return true;
            }
        }
        return false;
    }
    
    protected void onNewRound()
    {
        this.round++;
    }
    
    protected void onUserChanged()
    {
        this.thrown_dices_this_round = 0;
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
     private void dice()
     {
        boolean isInit;
        isInit = ((this.thrown_dices_this_round < 3) && (!this.getCurrentPlayer().isInGame()));
        if ((isInit) || (this.thrown_dices_this_round == 0)) {
            // This action is allowed
            this.last_diced_number = (int) (Math.random() * (this.dice_maximum)) + 1;
            this.board.setDice(this.last_diced_number, this.getCurrentPlayer().getColor());
            this.thrown_dices_this_round++;
            boolean next_not_init         = !isInit;
            boolean next_init_six_thrown  = ((isInit) && (this.last_diced_number == 6));
            boolean next_init_round_ended = ((isInit) && (this.thrown_dices_this_round >= 3));
            if (next_init_six_thrown) {
                // Init and six is thrown
                this.last_diced_number = 1;
                this.setCurrentAction(AVAILABLE_ACTIONS.CHOOSE_FIGURE);
            } else if (next_not_init) {
                this.setCurrentAction(AVAILABLE_ACTIONS.CHOOSE_FIGURE);
            } else if (next_init_round_ended) {
                // Player is not allowed to select a figure to move
                this.switchToNextPlayer();
            }
        } else {
            // Not allowed to throw the dice
            this.last_diced_number = 0;
        }
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

    /**
     * Returns the Field which is steps next to the source
     * @param source Source from which we have to read the next position
     * @param steps Amount of steps
     * @return Field
     */
    protected BoardElement calculateBoardElement(BoardElement source, int steps) {
        if (steps > 0) {
            Coordinate nextCoordinate = source.getNextElementPosition();
            if (source instanceof BoardElementWaypoint) {
                if (this.board.isAlternativeAvailable(source.getPosition(), this.activePlayer)) {
                    Coordinate temp = ((BoardElementWaypoint)source).getAlternative();
                    if (temp != null) {
                        nextCoordinate = temp;
                    }
                }
            }
            if (nextCoordinate == null) {
                // To many steps / not enough fields
                return null;
            }
            source = this.calculateBoardElement(this.board.getBoardElement(nextCoordinate), (steps - 1));
        }
        return source;
    }

    public void userDice()
    {
        if (this.isWaitingForDice()) {
            Logger.write(String.format("Wurf vom Nutzer durchgefuehrt"));
            this.dice();
        }
    }

    @Override
    public boolean moveFigur(Coordinate position, Figur figur, int steps) {
        if (this.isWaitingForChooseFigure()) {
            Figur target = this.getFigur(figur, this.activePlayer);
            if (target != null) {
                BoardElement currentField, targetField;
                currentField = this.board.getBoardElement(position);
                targetField = this.calculateBoardElement(currentField, steps);
                if (targetField.isOccupied()) {
                    Logger.write(String.format("Target is occupied already!"));
                    // @todo: Field is occupied by another figure
                    return false;
                }
                target.addSteps(steps);
                targetField.occupie(target);
                currentField.free();
                this.setCurrentAction(AVAILABLE_ACTIONS.NONE);
                Logger.write(String.format("Figur (%s) moved %d fields", target, steps));
                this.postFigurMove();
                this.board.setDice(0, this.getCurrentPlayer().getColor());
                return true;
            } else {
                Logger.write(String.format("Player %s has to do this move!", this.players[this.activePlayer]));
            }
        } else {
            // Not waiting for interaction
            Logger.write(String.format("Can not move figure %s, not waiting for interaction", figur));
        }
        return false;
    }

    private void playerSwitch()
    {
        // @todo: Implement player-switch-code
        if (!this.switchToNextPlayer()) {
            Logger.write(String.format("Unknown error while switching to the next player"));
        }
    }

    private void postFigurMove()
    {
        this.playerSwitch();
        this.setCurrentAction(AVAILABLE_ACTIONS.DICE);
    }

    private Figur getFigur(Figur figur)
    {
        int i;
        for (i = 0; i < this.players.length; i++) {
            if (this.getFigur(figur, i) != null) {
                return this.getFigur(figur, i);
            }
        }
        return null;
    }
    
    public Player getPlayer(int id)
    {
        return this.players[id];
    }

    /**
     * Compares the figure figur with the playerfigures and returns the correct playerfigure if found
     * @param figur Figure to return from the playerfigures
     * @return Playerfigure
     */    
    private Figur getFigur(Figur figur, int i)
    {
        int k;
        for (k = 0; k < this.players[i].getFigures().length; k++)  {
            if (figur.equals(this.players[i].getFigures()[k])) {
                // Found the correct figure
                return this.players[i].getFigures()[k];
            }
        }
        return null;
    }
    
}

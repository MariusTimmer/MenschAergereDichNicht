package de.timmer.menschaergeredichnicht;

    /**
     *
     * Mensch aergere dich nicht
     *
     * @author Marius Timmer <admin@MariusTimmer.de>
     * @version 0.0.1
     */

import de.timmer.menschaergeredichnicht.Logger;

public class MenschAergereDichNicht {

    /**
     * If no number is submitted by command line arguments
     * we assume to have DEFAULT_NUMBER_OF_PLAYERS player
     */
    public static final int DEFAULT_NUMBER_OF_PLAYERS = 4;
    
    /**
     * File that contains map-data
     */
    public static final String MAP_SOURCE = "res/board_p4.dat";

    private static Controller gamecontroller;
    
    public static void main(String[] args) {
        Logger.write("Dateien einlesen");
        Logger.write("Gamecontroller starten");
        if (args.length > 0) {
            Logger.write("Lese Anzahl der Spieler aus Kommandozeile aus");
            try {
                gamecontroller = new Controller(Integer.parseInt(args[0]));
            } catch (NumberFormatException e) {
                Logger.write("Argument war keine Zahl; "+ e.getMessage());
                gamecontroller = new Controller(DEFAULT_NUMBER_OF_PLAYERS);
            }
        } else {
            Logger.write("Nutze Standardanzahl von "+ DEFAULT_NUMBER_OF_PLAYERS);
            gamecontroller = new Controller(DEFAULT_NUMBER_OF_PLAYERS);
        }
    }

}
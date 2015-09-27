package de.lebk.madn;

/**
 * This is the main-Class for "Mensch aergere dich nicht"
 *
 * From the root-directory you need to run these commands:
 * javac de/lebk/madn/MenschAergereDichNicht.java
 * java de.lebk.madn.MenschAergereDichNicht
 *
 * This Program is a project written by:
 *  - Marius Timmer <admin@MariusTimmer.de>
 *  - Marvin Fischer
 *  - Jan-Niklas Müller-Fahlbusch
 *  - Jannik Holmer
 */

public class MenschAergereDichNicht {

    private static final String		DEFAULT_MAP_FILE			= "res/board_p4.dat";	// Default mapfile
    private static final int		DEFAULT_NUMBER_OF_PLAYERS	= 4;					// Default number of players

    /**
     * This is the main-method for the game
     * special arguments are supported but not needed.
     * After reading the command-line-arguments the game will be created
     */
    public static void main(String[] args) {
        Logger.write("\"Mensch aergere dich nicht\" started");
        String map_file = DEFAULT_MAP_FILE;
        int number_of_players = DEFAULT_NUMBER_OF_PLAYERS;
        for (String argument : args) {
            // Read the command-line-arguments
            if (argument.contains("=")) {
                // Key-value-Paar vorhanden
                String key = argument.split("=")[0].toUpperCase();
                String value = argument.split("=")[1];
                if (key.equals("MAPFILE")) {
                    // Use a special map-file
                    map_file = value;
                } else if (key.equals("NOP")) {
                    // Use a special number of players
                    try {
                        number_of_players = Integer.parseInt(value);
                    } catch (Exception e) {
                        // Casting the String to int failed
                        Logger.write("NOP-Value is not a number!");
                    }
                }
            }
        }
        Logger.write(String.format("Bereite Objekte und Karte \"%s\" fuer %d Spieler vor", map_file, number_of_players));
        Spiel spiel;
        spiel = new Spiel(number_of_players, map_file);		// Create a new Game-Object
        // Logger.write(spiel.toString());
    }

}

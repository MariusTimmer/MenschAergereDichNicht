package de.lebk.madn;

public class MenschAergereDichNicht {

    private static final String DEFAULT_MAP_FILE = "res/board_4p.dat";
    private static final int DEFAULT_NUMBER_OF_PLAYERS = 4;

    public static void main(String[] args) {
        Logger.write("\"Mensch aergere dich nicht\" wurde gestartet");
        String map_file = DEFAULT_MAP_FILE;
        int number_of_players = DEFAULT_NUMBER_OF_PLAYERS;
        for (String argument : args) {
            if (argument.contains("=")) {
                // Key-value-Paar vorhanden
                String key = argument.split("=")[0].toUpperCase();
                String value = argument.split("=")[1];
                if (key.equals("MAPFILE")) {
                    map_file = value;
                } else if (key.equals("NOP")) {
                    try {
                        number_of_players = Integer.parseInt(value);
                    } catch (Exception e) {
                        Logger.write("NOP-Wert ist keine Zahl!");
                    }
                }
            }
        }
        Logger.write(String.format("Bereite Objekte und Karte \"%s\" fuer %d Spieler vor", map_file, number_of_players));
        Spiel spiel;
        spiel = new Spiel(number_of_players, map_file);
        Logger.write(spiel.toString());
    }

}

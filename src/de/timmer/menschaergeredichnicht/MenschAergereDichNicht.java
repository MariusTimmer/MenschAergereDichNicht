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
    
    public static void main(String[] args) {
        Logger.write("Programm started");
    }

}
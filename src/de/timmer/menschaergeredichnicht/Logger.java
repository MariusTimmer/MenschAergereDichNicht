package de.timmer.menschaergeredichnicht;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class Logger {

    /**
     * Writes a message to the log
     * @param message The text that should be written to the log
     * @return True if success or false
     */
    public static boolean write(String message) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            writer.write(message +"\n");
            writer.flush();
            return true;
        } catch (Exception e) {
            /* An error occured while trying to write something to stdout */
            return false;
        }
    }

}
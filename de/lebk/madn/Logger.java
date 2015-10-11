package de.lebk.madn;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

/**
 * Logger-Class
 *
 * This class provides a formated String-output for logging
 *
 * @author Marius Timmer <admin@MariusTimmer.de>
 */

public class Logger {
	
	private static int log_line = 0;
	
	/**
	 * Writes a message to the standardoutput
	 * @param message Message to write
	 * @return true if succeed or false if an error occured
	 */
	public static boolean write(String message) {
		log_line++;
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
			writer.write(String.format("%06d: %s\n", log_line, message));
			writer.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Writes a message and its sender-class to the standardoutput
	 * @param sender The sender of the message (Mostly "this")
	 * @param message Message to write
	 * @return True if succeed of false if an error occured
	 */
	public static boolean write(Object sender, String message) {
		return Logger.write(sender.getClass().getSimpleName(), message);
	}
	
}
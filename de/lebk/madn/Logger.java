package de.lebk.madn;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class Logger {
	
	private static int log_line = 0;
	
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
	
}
package de.lebk.madn;

import java.awt.Color;
import de.lebk.madn.model.Player;

public class Spiel {
	
	public static final Color[] AVAILABLE_COLORS= {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
	private Player[] players;
	
	public Spiel(int number_of_players) {
		this.initPlayers(number_of_players);
	}
	
	private void initPlayers(int number_of_players) {
		int i;
		this.players = new Player[number_of_players];
		for (i = 0; i < number_of_players; i++) {
			this.players[i] = new Player(AVAILABLE_COLORS[i % AVAILABLE_COLORS.length]);
		}
	}
	
}
package de.lebk.madn.model;

import java.awt.Color;
import java.lang.Comparable;
import de.lebk.madn.Logger;

public class Player implements Comparable<Player> {
	
	private static final int DEFAULT_NUMBER_OF_FIGURES = 4;
	private Color color;
	private String name;
	private Figur[] figures;
	
	public Player(Color color) {
		this.color = color;
		this.initFigures(DEFAULT_NUMBER_OF_FIGURES);
	}
	
	public Player(Color color, String name) {
		this(color);
		this.name = name;
	}
	
	private void initFigures(int nof) {
		this.figures = new Figur[nof];
		for (Figur figur: this.figures) {
			figur = new Figur(this.color);
		}
	}
	
	@Override
	public int compareTo(Figur other) {
		Logger.write("Player::compareTo noch nicht implementiert");
		return 0;
	}
	
}
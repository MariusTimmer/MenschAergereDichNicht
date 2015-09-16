package de.lebk.madn.model;

import java.awt.Color;
import java.lang.Comparable;

public class Figur implements Comparable<Figur> {
	
	private Color color;
	private int position;

	public Figur(Color color) {
		this.color = color;
		this.position = 0;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getPosition() {
		return this.position;
	}
	
	public void addSteps(int steps) {
		this.position += steps;
	}
	
	@Override
	public int compareTo(Figur other) {
		return Integer.compare(this.position, other.position);
	}
	
}
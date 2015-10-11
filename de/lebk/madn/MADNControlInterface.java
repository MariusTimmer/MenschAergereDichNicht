package de.lebk.madn;

import de.lebk.madn.model.Figur;

public interface MADNControlInterface {

    public boolean moveFigur(Coordinate position, Figur figur, int steps);

}
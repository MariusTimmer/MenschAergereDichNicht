package de.lebk.madn;

import de.lebk.madn.model.Figur;
import de.lebk.madn.model.Player;

public interface MADNControlInterface {

    public boolean moveFigur(Coordinate position, Figur figur, int steps);
    public void userDice();
    public Player getPlayer(int id);

}
package de.lebk.madn;

public class MapException extends MenschAergereDichNichtException {

    public MapException(String message) {
        super(String.format("MapError: %s", message));
    }

}
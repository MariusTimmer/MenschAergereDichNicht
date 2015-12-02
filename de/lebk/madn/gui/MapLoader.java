package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import de.lebk.madn.Logger;
import de.lebk.madn.Spiel;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class MapLoader {

    private static enum FILE_PART {XPOS, YPOS, TYPE, NEXTX, NEXTY, COLOR, ALTERNATIVE_Y};
    private static final String PART_SEPARATOR = ";";
    String filename;

    public MapLoader(String filename) {
        this.filename = filename;
    }
    
    /**
     * Checks if the line contains an information for an alternative
     * @param line
     * @return True if an alternative is given or false
     */
    public boolean hasAlternative(String line)
    {
	return (line.split(PART_SEPARATOR).length == 7);
    }

    /**
     * Fetches the coordinate of the alternative following element
     * @param line Line from the mapfile
     * @return Coordinate of the alternative following element
     */
    public Coordinate getCoordinateForAlternative(String line) {
	if (!this.hasAlternative(line)) {
	    Logger.write(String.format("MapLoader::getCoordinateForAlternative: No alternative found"));
	    return null;
	}
        try {
            int x = Integer.valueOf(this.readPartFromLine(line, FILE_PART.COLOR));
            int y = Integer.valueOf(this.readPartFromLine(line, FILE_PART.ALTERNATIVE_Y));
            return new Coordinate(x, y);
        } catch (Exception e) {
            Logger.write(String.format("MapLoader::getCoordinateForNextElementFromLine: Can not parse a coordinate from line \"%s\": %s!", line, e.getMessage()));
            return null;
        }
    }

    private boolean isValid(String line) {
        return ((line.trim().length() > 0) && (line.charAt(0) != '#'));
    }
    
    public String[] readFile() {
        LinkedList<String> lines = new LinkedList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.filename));
            String line;
            while ((line = br.readLine()) != null) {
                if (this.isValid(line)) {
                    lines.add(line);
                }
            }
            br.close();
            return lines.toArray(new String[lines.size()]);
        } catch (FileNotFoundException e) {
            Logger.write(String.format("Couldn't find the file \"%s\"!", this.filename));
            return null;
        } catch (IOException e) {
            Logger.write(String.format("I/O-Error in mapfile \"%s\"!", this.filename));
            return null;
        }
    }
    
    public BoardElement.BOARD_ELEMENT_TYPES getTypeFromLine(String line) {
        String raw_type = this.readPartFromLine(line, FILE_PART.TYPE);
        try {
            Integer position = Integer.valueOf(raw_type);
            return BoardElement.BOARD_ELEMENT_TYPES.values()[position];
        } catch (Exception e) {
            Logger.write("MapLoader::getTypeFromLine could not parse: "+ e.getMessage());
            return null;
        }
    }

    /**
     * Fetches the color of the element (if it is a home or goal)
     * @param line Line from the mapfile
     * @return Color of the element
     */
    public Color getColorFromLine(String line) {
        try {
            int color_id = Integer.valueOf(this.readPartFromLine(line, FILE_PART.COLOR));
            return Spiel.AVAILABLE_COLORS[color_id];
        } catch (Exception e) {
            Logger.write(String.format("MapLoader::getColorFromLine: Can not parse a color from \"%s\"!", line));
            return null;
        }
    }

    /**
     * Fetches the coordinate of the following element
     * @param line Line from the mapfile
     * @return Coordinate of the following element
     */
    public Coordinate getCoordinateForNextElementFromLine(String line) {
        try {
            int x = Integer.valueOf(this.readPartFromLine(line, FILE_PART.NEXTX));
            int y = Integer.valueOf(this.readPartFromLine(line, FILE_PART.NEXTY));
            return new Coordinate(x, y);
        } catch (Exception e) {
            Logger.write(String.format("MapLoader::getCoordinateForNextElementFromLine: Can not parse a coordinate from line \"%s\"!", line));
            return null;
        }
    }
    
    /**
     * Fetches the Coordinate of the element
     * @param line Line from the mapfile
     * @return Coordinate of the element
     */
    public Coordinate getCoordinateFromLine(String line) {
        try {
            int x = Integer.valueOf(this.readPartFromLine(line, FILE_PART.XPOS));
            int y = Integer.valueOf(this.readPartFromLine(line, FILE_PART.YPOS));
            return new Coordinate(x, y);
        } catch (Exception e) {
            Logger.write(String.format("MapLoader::getCoordinateFromLine: Can not parse a coordinate from line \"%s\"!", line));
            return null;
        }
    }

    /**
     * Returns the wanted part from the line
     * @param line The line which should be parsed
     * @param typ The type of the part you want to have
     * @return The wantrd part from the line
     */
    private String readPartFromLine(String line, FILE_PART typ) {
	try {
            if ((this.isValid(line)) && (line.contains(PART_SEPARATOR)) && (typ != null)) {
                String[] parts = line.trim().split(PART_SEPARATOR);
                switch (typ) {
                    case YPOS:
                        return parts[0].trim();
                    case XPOS:
                        return parts[1].trim();
                    case TYPE:
                        return parts[2].trim();
                    case NEXTY:
                        return parts[3].trim();
                    case NEXTX:
                        return parts[4].trim();
                    case COLOR:
                        return parts[5].trim();
		    case ALTERNATIVE_Y:
                        return parts[6].trim();
                    default:
                        Logger.write("MapLoader::readPartFromLine unknown type!");
                        return null;
                }
            } else {
                // There is no part-separator in the line or the line is invalid
                Logger.write("MapLoader::readPartFromLine was trying to proccess with an invalid line!");
               return null;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Logger.write(String.format("MapLoader::readPartFromLine Nullpointer exception in line \"%s\": %s", line, e.getMessage()));
            return null;
        }
    }
    
    /**
     * Fetches the size of the map from the lines in the configfile
     * @param lines Lines from the mapfile
     * @return Width of the map
     */
    public int getMapSize(String[] lines) {
        int max = 0;
        for (String line: lines) {
            Coordinate c = this.getCoordinateFromLine(line);
            if (c != null) {
                if (c.getX() > max) {
                    max = c.getX();
                }
                if (c.getY() > max) {
                    max = c.getY();
                }
            }
        }
        return (max + 1);
    }

}

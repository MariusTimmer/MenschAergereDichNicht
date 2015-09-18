package de.lebk.madn.gui;

import de.lebk.madn.Coordinate;
import de.lebk.madn.Logger;
import de.lebk.madn.Spiel;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class MapLoader {

    private static enum FILE_PART {XPOS, YPOS, TYPE, NEXTX, NEXTY, COLOR};
    private static final String PART_SEPARATOR = ";";
    String filename;

    public MapLoader(String filename) {
        this.filename = filename;
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
        } catch (IOException e) {
            Logger.write(String.format("Fehler beim Lesen der Datei \"%s\"", this.filename));
            return null;
        }
    }
    
    public BoardElement.BOARD_ELEMENT_TYPES getTypeFromLine(String line) {
        String raw_type = this.readPartFromLine(line, FILE_PART.TYPE);
        try {
            Integer position = Integer.valueOf(raw_type);
            return BoardElement.BOARD_ELEMENT_TYPES.values()[position];
        } catch (Exception e) {
            Logger.write("MapLoader::getTypeFromLine konnte nicht parsen: "+ e.getMessage());
            return null;
        }
    }
    
    public Color getColorFromLine(String line) {
        try {
            int color_id = Integer.valueOf(this.readPartFromLine(line, FILE_PART.COLOR));
            return Spiel.AVAILABLE_COLORS[color_id];
        } catch (Exception e) {
            Logger.write(String.format("MapLoader::getColorFromLine: Kann keine Farbe aus der Zeile \"%s\" parsen!", line));
            return null;
        }
    }
    
    public Coordinate getCoordinateForNextElementFromLine(String line) {
        try {
            int x = Integer.valueOf(this.readPartFromLine(line, FILE_PART.NEXTX));
            int y = Integer.valueOf(this.readPartFromLine(line, FILE_PART.NEXTY));
            return new Coordinate(x, y);
        } catch (Exception e) {
            Logger.write(String.format("MapLoader::getCoordinateForNextElementFromLine: Kann keine Koordinate aus der Zeile \"%s\" parsen!", line));
            return null;
        }
    }
    
    public Coordinate getCoordinateFromLine(String line) {
        try {
            int x = Integer.valueOf(this.readPartFromLine(line, FILE_PART.XPOS));
            int y = Integer.valueOf(this.readPartFromLine(line, FILE_PART.YPOS));
            return new Coordinate(x, y);
        } catch (Exception e) {
            Logger.write(String.format("MapLoader::getCoordinateFromLine: Kann keine Koordinate aus der Zeile \"%s\" parsen!", line));
            return null;
        }
    }
    
    private String readPartFromLine(String line, FILE_PART typ) {
        if ((this.isValid(line)) && (line.contains(PART_SEPARATOR)) && (typ != null)) {
            String[] parts = line.split(PART_SEPARATOR);
            switch (typ) {
                case XPOS:
                    return parts[0];
                case YPOS:
                    return parts[1];
                case TYPE:
                    return parts[2];
                case NEXTX:
                    return parts[3];
                case NEXTY:
                    return parts[4];
                case COLOR:
                    return parts[5];
                default:
                    Logger.write("MapLoader::readPartFromLine bekam einen unbekannten Typen übergeben!");
                    return null;
            }
        } else {
            // There is no part-separator in the line or the line is invalid
            Logger.write("MapLoader::readPartFromLine hat eine ungültige Zeile bekommen!");
            return null;
        }
    }
    
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

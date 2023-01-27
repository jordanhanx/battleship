package edu.duke.xh123.battleship;

/**
 * This is the type of placement of ships in our Battleship game.
 */
public class Placement {
    private final Coordinate cood;
    private final char orient;

    /**
     * Constructs a Placement with a Coordinate and a char
     * 
     * @param cood   is the Coordinate of the newly constructed Placement.
     * @param orient is the orientation of the newly constructed Placement.
     * @throws IllegalArgumentException if orientation is illegal (neither 'H'/'h'
     *                                  nor 'V'/'v')
     */
    public Placement(Coordinate c, char o) {
        char upper_o = Character.toUpperCase(o);
        if (upper_o != 'H' && upper_o != 'V') {
            throw new IllegalArgumentException("Orientation should be either 'H'/'h' or 'V'/'v' but is " + o);
        }
        this.cood = c;
        this.orient = upper_o;
    }

    /**
     * Constructs a Coordinate with the description String like "A2"
     * that corresponds to the coordinate: row=0, column=2.
     * 
     * @param descr is the description of the newly constructed Coordinate.
     * @throws IllegalArgumentException if the description is illegal.
     */
    public Placement(String descr) {
        // this(new Coordinate(descr.substring(0, 2)), descr.charAt(2));
        if (descr.length() != 3) {
            throw new IllegalArgumentException("Placement's description must be like 'A9H' but is " + descr);
        }
        char upper_o = Character.toUpperCase(descr.charAt(2));
        if (upper_o != 'H' && upper_o != 'V') {
            throw new IllegalArgumentException("Orientation should be either 'H'/'h' or 'V'/'v' but is " + descr);
        }
        this.cood = new Coordinate(descr.substring(0, 2));
        this.orient = upper_o;
    }

    public Coordinate getCoordinate() {
        return cood;
    }

    public char getOrientation() {
        return orient;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass().equals(getClass())) {
            Placement p = (Placement) o;
            return cood.equals(p.cood) && orient == p.orient;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return "coordination: " + cood + ", orientation: " + orient;
    }
}

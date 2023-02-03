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
     * @param c is the Coordinate of the newly constructed Placement.
     * @param o is the orientation of the newly constructed Placement.
     * @throws IllegalArgumentException if Coordinate is illegal
     */
    public Placement(Coordinate c, char o) {
        this.cood = c;
        this.orient = Character.toUpperCase(o);
    }

    /**
     * Constructs a Placement with the description String like "A0V"
     * 
     * @param descr is the description of the newly constructed Coordinate.
     * @throws IllegalArgumentException if the description is illegal.
     */
    public Placement(String descr) {
        if (descr.length() != 3) {
            throw new IllegalArgumentException(
                    "Placement's description must be the same length as 'A0V' but is " + descr);
        }
        this.cood = new Coordinate(descr.substring(0, 2));
        this.orient = Character.toUpperCase(descr.charAt(2));
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
        char r = (char) ('A' + cood.getRow());
        char c = (char) ('0' + cood.getColumn());
        return "" + r + c + orient;
    }
}

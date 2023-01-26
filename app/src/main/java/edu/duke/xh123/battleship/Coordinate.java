package edu.duke.xh123.battleship;

/**
 * This is the type of coordinate representing the location of ships in our
 * Battleship game.
 */
public class Coordinate {
    private final int row;
    private final int column;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    /**
     * Constructs a Coordinate with the specified row number
     * and column number
     * 
     * @param r is the #row of the newly constructed Coordinate.
     * @param c is the #column of the newly constructed Coordinate.
     * @throws IllegalArgumentException if the #row or #column are less than or
     *                                  equal to zero.
     */
    public Coordinate(int r, int c) {
        if (r < 0) {
            throw new IllegalArgumentException("Coordinate's row number cannot be negtive but is " + r);
        }
        if (c < 0) {
            throw new IllegalArgumentException("Coordinate's column number cannot be negtive but is " + c);
        }
        this.row = r;
        this.column = c;
    }

    /**
     * Constructs a Coordinate with the description String like "A2"
     * that corresponds to the coordinate: row=0, column=2.
     * 
     * @param descr is the description of the newly constructed Coordinate.
     * @throws IllegalArgumentException if the description is illegal.
     */
    public Coordinate(String descr) {
        if (descr.length() != 2) {
            throw new IllegalArgumentException("Coordinate's description must be like 'A9' but is " + descr);
        }
        char rowLetter = descr.toUpperCase().charAt(0);
        char columnNum = descr.charAt(1);
        if (rowLetter < 'A' || rowLetter > 'Z') {
            throw new IllegalArgumentException("Coordinate's row must between 'A' and 'Z' but is " + descr);
        }
        if (columnNum < '0' || columnNum > '9') {
            throw new IllegalArgumentException("Coordinate's column must between '0' and '9' but is " + descr);
        }
        this.row = rowLetter - 'A';
        this.column = columnNum - '0';
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            Coordinate c = (Coordinate) o;
            return row == c.row && column == c.column;
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
        return "(" + row + ", " + column + ")";
    }
}

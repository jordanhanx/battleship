package edu.duke.xh123.battleship;

import java.util.HashMap;

/**
 * This is an basic type of Ship occupying in our Battleship Game.
 */
public abstract class BasicShip<T> implements Ship<T> {
    protected HashMap<Coordinate, Boolean> myPieces;
    protected ShipDisplayInfo<T> myDisplayInfo;

    /**
     * Constructs a BasicShip with some Iterable Coordinates
     * 
     * @param where         is some Iterable Coordinates occupied by the ship .
     * @param myDisplayInfo is the ship's display information.
     */
    public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo) {
        this.myPieces = new HashMap<>();
        this.myDisplayInfo = myDisplayInfo;
        for (Coordinate c : where) {
            this.myPieces.put(c, false);
        }
    }

    /**
     * Checks if the Coordinate is in this ship.
     * 
     * @param c is the coordinate to be checked.
     * @throws IllegalArgumentException if the Coordinate is not in this ship.
     */
    protected void checkCoordinateInThisShip(Coordinate c) {
        if (!myPieces.containsKey(c)) {
            throw new IllegalArgumentException("Coordinate" + c.toString() + " is not in this ship");
        }
    }

    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        return myPieces.containsKey(where);
    }

    @Override
    public boolean isSunk() {
        return !myPieces.containsValue(false);
    }

    @Override
    public void recordHitAt(Coordinate where) {
        checkCoordinateInThisShip(where);
        myPieces.put(where, true);
    }

    @Override
    public boolean wasHitAt(Coordinate where) {
        checkCoordinateInThisShip(where);
        return myPieces.get(where);
    }

    @Override
    public T getDisplayInfoAt(Coordinate where) {
        checkCoordinateInThisShip(where);
        return myDisplayInfo.getInfo(where, wasHitAt(where));
    }

}

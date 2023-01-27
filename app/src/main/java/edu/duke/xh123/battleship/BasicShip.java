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

    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        return myPieces.containsKey(where);
    }

    @Override
    public boolean isSunk() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void recordHitAt(Coordinate where) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean wasHitAt(Coordinate where) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public T getDisplayInfoAt(Coordinate where) {
        // TODO this is not right. We need to
        // look up the hit status of this coordinate
        return myDisplayInfo.getInfo(where, false);
    }

}

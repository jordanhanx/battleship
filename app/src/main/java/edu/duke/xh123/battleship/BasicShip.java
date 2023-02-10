package edu.duke.xh123.battleship;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * This is an basic type of Ship occupying in our Battleship Game.
 */
public abstract class BasicShip<T> implements Ship<T> {
    protected final String name;
    protected Placement myPlacement;
    protected HashMap<Coordinate, Boolean> myPieces;
    protected ShipDisplayInfo<T> myDisplayInfo;
    protected ShipDisplayInfo<T> enemyDisplayInfo;

    /**
     * Constructs a BasicShip with some Iterable Coordinates
     * 
     * @param name                is the ship's name.
     * @param myPlacement         is the ship's placement.
     * @param relativeCoordinates is some Iterable Coordinates occupied by the ship
     * @param myDisplayInfo       is the ship's display information for self.
     * @param enemyDisplayInfo    is the ship's display information for enemy.
     */
    public BasicShip(String name, Placement myPlacement, Iterable<Coordinate> relativeCoordinates,
            ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        this.name = name;
        this.myPlacement = myPlacement;
        this.myPieces = new HashMap<>();
        for (Coordinate c : relativeCoordinates) {
            this.myPieces.put(c, false);
        }
        this.myDisplayInfo = myDisplayInfo;
        this.enemyDisplayInfo = enemyDisplayInfo;
    }

    /**
     * Checks if the Coordinate is in this ship.
     * 
     * @param c is the coordinate to be checked.
     * @throws IllegalArgumentException if the Coordinate is not in this ship.
     */
    protected void checkCoordinateInThisShip(Coordinate c) {
        if (!myPieces.containsKey(convertToRelative(c))) {
            throw new IllegalArgumentException("Coordinate" + c.toString() + " is not in this ship");
        }
    }

    /**
     * Converts absolute coordinate to relative coordinate.
     * 
     * @param absolute is absolute coordinate.
     * @return relative coordinate
     */
    private Coordinate convertToRelative(Coordinate absolute) {
        return new Coordinate(absolute.getRow() - myPlacement.getCoordinate().getRow(),
                absolute.getColumn() - myPlacement.getCoordinate().getColumn());
    }

    /**
     * Converts relative coordinate to absolute coordinate.
     * 
     * @param absolute is relative coordinate.
     * @return absolute coordinate
     */
    private Coordinate convertToAbsolute(Coordinate relative) {
        return new Coordinate(relative.getRow() + myPlacement.getCoordinate().getRow(),
                relative.getColumn() + myPlacement.getCoordinate().getColumn());
    }

    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        return myPieces.containsKey(convertToRelative(where));
    }

    @Override
    public boolean isSunk() {
        return !myPieces.containsValue(false);
    }

    @Override
    public void recordHitAt(Coordinate where) {
        checkCoordinateInThisShip(where);
        myPieces.put(convertToRelative(where), true);
    }

    @Override
    public boolean wasHitAt(Coordinate where) {
        checkCoordinateInThisShip(where);
        return myPieces.get(convertToRelative(where));
    }

    @Override
    public T getDisplayInfoAt(Coordinate where, boolean myShip) {
        checkCoordinateInThisShip(where);
        if (myShip) {
            return myDisplayInfo.getInfo(where, wasHitAt(where));
        } else {
            return enemyDisplayInfo.getInfo(where, wasHitAt(where));
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Iterable<Coordinate> getCoordinates() {
        HashSet<Coordinate> coordinateSet = new HashSet<>();
        for (Coordinate relative : myPieces.keySet()) {
            coordinateSet.add(convertToAbsolute(relative));
        }
        return coordinateSet;
    }

    @Override
    public Placement getPlacement() {
        return myPlacement;
    }

    /**
     * Get the current Height of the rectangle area which envelops the ship.
     * 
     * @return the height.
     */
    private int getHeight() {
        int h = 0;
        for (Map.Entry<Coordinate, Boolean> pair : myPieces.entrySet()) {
            if (pair.getKey().getRow() > h) {
                h = pair.getKey().getRow();
            }
        }
        return h;
    }

    @Override
    public void rotateQuarterClockwise() {
        int height = getHeight();
        HashMap<Coordinate, Boolean> newPieces = new HashMap<>();
        for (Map.Entry<Coordinate, Boolean> pair : myPieces.entrySet()) {
            Coordinate afterRotate = new Coordinate(pair.getKey().getColumn(), height - pair.getKey().getRow());
            newPieces.put(afterRotate, pair.getValue());
        }
        myPieces = newPieces;
        myPlacement = myPlacement.rotateQuarterClockwise();
    }
}

package edu.duke.xh123.battleship;

import java.util.ArrayList;

/**
 * This is an specific type of Board having several ships and const size in our
 * Battleship game.
 */
public class BattleShipBoard<T> implements Board<T> {
    private final int width;
    private final int height;
    private final ArrayList<Ship<T>> myShips;
    private final PlacementRuleChecker<T> placementChecker;

    /**
     * Constructs a BattleShipBoard with the specified width
     * and height, and a specified placement rules checker.
     * 
     * @param w       is the width of the newly constructed board.
     * @param h       is the height of the newly constructed board.
     * @param checker is the placement rules checker.
     * @throws IllegalArgumentException if the width or height are less than or
     *                                  equal to zero.
     */
    public BattleShipBoard(int w, int h, PlacementRuleChecker<T> checker) {
        if (w <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
        }
        if (h <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + h);
        }
        this.width = w;
        this.height = h;
        this.myShips = new ArrayList<Ship<T>>();
        this.placementChecker = checker;
    }

    /**
     * Constructs a BattleShipBoard with the specified width
     * and height, using the out of bounds checking as the default placement rule
     * checker.
     * 
     * @param w is the width of the newly constructed board.
     * @param h is the height of the newly constructed board.
     * @throws IllegalArgumentException if the width or height are less than or
     *                                  equal to zero.
     */
    public BattleShipBoard(int w, int h) {
        this(w, h, new InBoundsRuleChecker<T>(null));
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean tryAddShip(Ship<T> toAdd) {
        myShips.add(toAdd);
        return true;
    }

    @Override
    public T whatIsAt(Coordinate where) {
        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(where)) {
                return s.getDisplayInfoAt(where);
            }
        }
        return null;
    }
}
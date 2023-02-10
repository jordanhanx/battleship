package edu.duke.xh123.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is an specific type of Board having several ships and const size in our
 * Battleship game.
 */
public class BattleShipBoard<T> implements Board<T> {
    private final int width;
    private final int height;
    private final ArrayList<Ship<T>> myShips;
    private final PlacementRuleChecker<T> placementChecker;
    private HashMap<Coordinate, T> enemyMarkers;
    final T missInfo;

    /**
     * Constructs a BattleShipBoard with the specified width
     * and height, and a specified placement rules checker.
     * 
     * @param w        is the width of the newly constructed board.
     * @param h        is the height of the newly constructed board.
     * @param checker  is the placement rules checker.
     * @param missInfo is the display info of missed hit.
     * @throws IllegalArgumentException if the width or height are less than or
     *                                  equal to zero.
     */
    public BattleShipBoard(int w, int h, PlacementRuleChecker<T> checker, T missInfo) {
        if (w <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
        }
        if (h <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + h);
        }
        this.width = w;
        this.height = h;
        this.myShips = new ArrayList<>();
        this.placementChecker = checker;
        this.enemyMarkers = new HashMap<>();
        this.missInfo = missInfo;
    }

    /**
     * Constructs a BattleShipBoard with the specified width
     * and height, using the out of bounds checking as the default placement rule
     * checker.
     * 
     * @param w        is the width of the newly constructed board.
     * @param h        is the height of the newly constructed board.
     * @param missInfo is the display info of missed hit.
     * @throws IllegalArgumentException if the width or height are less than or
     *                                  equal to zero.
     */
    public BattleShipBoard(int w, int h, T missInfo) {
        this(w, h, new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<T>(null)), missInfo);
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
    public String tryAddShip(Ship<T> toAdd) {
        String msg = placementChecker.checkPlacement(toAdd, this);
        if (msg == null) {
            myShips.add(toAdd);
        }
        return msg;
    }

    /**
     * Get the information the view needs at the specific coordinate.
     * 
     * @param where  is the Coordinate to find information.
     * @param isSelf is whether show self view.
     * @return the information the view needs, or null if nothing was at the
     *         coordinate.
     */
    protected T whatIsAt(Coordinate where, boolean isSelf) {
        if (isSelf) {
            for (Ship<T> s : myShips) {
                if (s.occupiesCoordinates(where)) {
                    return s.getDisplayInfoAt(where, isSelf);
                }
            }
            return null;
        } else {
            return enemyMarkers.get(where);
        }
    }

    @Override
    public T whatIsAtForSelf(Coordinate where) {
        return whatIsAt(where, true);
    }

    @Override
    public T whatIsAtForEnemy(Coordinate where) {
        return whatIsAt(where, false);
    }

    @Override
    public Ship<T> fireAt(Coordinate c) {
        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(c)) {
                s.recordHitAt(c);
                enemyMarkers.put(c, s.getDisplayInfoAt(c, false));
                return s;
            }
        }
        enemyMarkers.put(c, missInfo);
        return null;
    }

    @Override
    public boolean shipsAreAllSunk() {
        for (Ship<T> s : myShips) {
            if (!s.isSunk()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Ship<T> whichShipIsAt(Coordinate c) {
        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(c) && !s.isSunk()) {
                return s;
            }
        }
        return null;
    }

    @Override
    public String tryMoveShip(Ship<T> toMove, Placement destination) {
        Placement origin = toMove.getPlacement();
        toMove.moveTo(destination);
        myShips.remove(toMove);
        String msg = placementChecker.checkPlacement(toMove, this);
        if (msg == null) {
            myShips.add(toMove);
        } else {
            toMove.moveTo(origin);
            myShips.add(toMove);
        }
        return msg;
    }

    @Override
    public Map<String, Integer> sonarScanAt(Coordinate center) {
        HashMap<String, Integer> scanResult = new HashMap<>();
        for (Ship<T> ship : myShips) {
            scanResult.putIfAbsent(ship.getName(), 0);
        }
        for (int row = center.getRow() - 3; row <= center.getRow() + 3; row++) {
            for (int col = center.getColumn() - 3 + Math.abs(row - center.getRow()); col <= center.getColumn() + 3
                    - Math.abs(row - center.getRow()); col++) {
                Ship<T> s = whichShipIsAt(new Coordinate(row, col));
                if (s != null) {
                    int value = scanResult.get(s.getName());
                    scanResult.put(s.getName(), value + 1);
                }
            }
        }
        return scanResult;
    }
}
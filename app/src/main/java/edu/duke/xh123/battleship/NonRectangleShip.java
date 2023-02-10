package edu.duke.xh123.battleship;

import java.util.HashSet;
import java.util.Set;

/**
 * This is an non-rectangle Ship.
 */
public class NonRectangleShip<T> extends BasicShip<T> {
    /**
     * This static method should generate the set of coordinates for a non-rectangle
     * starting at upperLeft whose width and height are as specified.
     * 
     * @param width  is the rectangle's width.
     * @param height is the rectangle's height.
     * @param remove is the set of coordinates that should be removed from a
     *               rectangle ship.
     * @return a set of coordinates for a non-rectangle.
     */
    static HashSet<Coordinate> makeCoords(int width, int height, Set<Coordinate> remove) {
        HashSet<Coordinate> cood_set = new HashSet<>();
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                Coordinate c = new Coordinate(row, col);
                if (!remove.contains(c)) {
                    cood_set.add(c);
                }
            }
        }
        return cood_set;
    }

    /**
     * Constructs a non-rectangle ship with specified upperLeft coordinate, width,
     * height and a set of coordinates that should be removed.
     * 
     * @param name             is the name of this ship.
     * @param placement        is the placement of the ship.
     * @param width            is the rectangle's width.
     * @param height           is the rectangle's height.
     * @param remove           is the set of coordinates that should be removed from
     *                         a rectangle ship.
     * @param myDisplayInfo    is the ship's self display information.
     * @param enemyDisplayInfo is the ship's display information for enemy.
     */
    public NonRectangleShip(String name, Placement placement, int width, int height, Set<Coordinate> remove,
            ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        super(name, placement, makeCoords(width, height, remove), myDisplayInfo, enemyDisplayInfo);
    }

    /**
     * Constructs a non-rectangle ship with specified upperLeft coordinate, width,
     * height and a set of coordinates that should be removed.
     * 
     * @param name      is the name of this ship.
     * @param placement is the placement of the ship.
     * @param width     is the rectangle's width.
     * @param height    is the rectangle's height.
     * @param remove    is the set of coordinates that should be removed from a
     *                  rectangle ship.
     * @param myData    is the display info of the ship's unhit part.
     * @param onHit     is the display info of the ship's hit part.
     */
    public NonRectangleShip(String name, Placement placement, int width, int height, Set<Coordinate> remove, T data,
            T onHit) {
        this(name, placement, width, height, remove, new SimpleShipDisplayInfo<T>(data, onHit),
                new SimpleShipDisplayInfo<T>(null, data));
    }
}

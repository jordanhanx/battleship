package edu.duke.xh123.battleship;

import java.util.HashSet;

/**
 * This is an specific type of Ship that is ractangle in our Battleship game.
 */
public class RectangleShip<T> extends BasicShip<T> {
    private final String name;

    /**
     * This static method should generate the set of coordinates for a rectangle
     * starting at upperLeft whose width and height are as specified.
     * 
     * @param upperLeft is the coordinate of rectangle's upper left corner.
     * @param width     is the rectangle's width.
     * @param height    is the rectangle's height.
     * @return a set of coordinates for a rectangle.
     */
    static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
        HashSet<Coordinate> cood_set = new HashSet<>();
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                cood_set.add(new Coordinate(upperLeft.getRow() + row, upperLeft.getColumn() + col));
            }
        }
        return cood_set;
    }

    /**
     * Constructs a rectangle ship with specified upperLeft coordinate, width and
     * height.
     * 
     * @param name          is the name of this ship.
     * @param upperLeft     is the coordinate of rectangle's upper left corner.
     * @param width         is the rectangle's width.
     * @param height        is the rectangle's height.
     * @param myDisplayInfo is the ship's display information.
     */
    public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo) {
        super(makeCoords(upperLeft, width, height), myDisplayInfo);
        this.name = name;
    }

    /**
     * Constructs a rectangle ship with specified upperLeft coordinate, width and
     * height.
     * 
     * @param name      is the name of this ship.
     * @param upperLeft is the coordinate of rectangle's upper left corner.
     * @param width     is the rectangle's width.
     * @param height    is the rectangle's height.
     * @param myData    is the display info of the ship's unhit part.
     * @param onHit     is the display info of the ship's hit part.
     */
    public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
        this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit));
    }

    /**
     * Constructs a test ship (both width and default are 1) with specified
     * upperLeft coordinate.
     * 
     * @param upperLeft is the coordinate of rectangle's upper left corner.
     * @param myData    is the display info of the ship's unhit part.
     * @param onHit     is the display info of the ship's hit part.
     */
    public RectangleShip(Coordinate upperLeft, T data, T onHit) {
        this("testship", upperLeft, 1, 1, data, onHit);
    }

    public String getName() {
        return name;
    }
}

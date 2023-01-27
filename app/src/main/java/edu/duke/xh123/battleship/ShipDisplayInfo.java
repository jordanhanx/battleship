package edu.duke.xh123.battleship;

/**
 * This interface represents any type of Ship's display information in our
 * Battleship game. It is generic in typename T, which is the type of
 * information the view needs to display this ship.
 */
public interface ShipDisplayInfo<T> {
    /**
     * gets display information at the specified coordinate.
     * 
     * @param where is the specified coordinate.
     * @param hit   is if this coordinate has been hit.
     * @return T type of Ship's display information.
     */
    public T getInfo(Coordinate where, boolean hit);
}

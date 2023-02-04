package edu.duke.xh123.battleship;

/**
 * This interface represents any type of Board in our Battleship game. It is
 * generic in typename T, which is the type of information the view needs to
 * display this board.
 */
public interface Board<T> {
    public int getWidth();

    public int getHeight();

    /**
     * Try to add a ship into the board.
     * 
     * @param toAdd is the ship to be added in the board.
     * @return null if the ship be added successfully, a string indicating what was
     *         wrong if not.
     */
    public String tryAddShip(Ship<T> toAdd);

    /**
     * Get the information the self view needs at the specific coordinate.
     * 
     * @param where is the Coordinate to find information.
     * @return the information the view needs, or null if nothing was at the
     *         coordinate.
     */
    public T whatIsAtForSelf(Coordinate where);

    /**
     * Get the information the enemy view needs at the specific coordinate.
     * 
     * @param where is the Coordinate to find information.
     * @return the information the view needs, or null if nothing was at the
     *         coordinate.
     */
    public T whatIsAtForEnemy(Coordinate where);

    /**
     * Try to "shoot" at something on the board
     * 
     * 
     * @param c is the coordinate to be shot in the board.
     * @return the ship if it is be shot successfully, else return null
     */
    public Ship<T> fireAt(Coordinate c);

    /**
     * Checks if all ships on this board have sunk.
     * 
     * @return true if all sunk.
     */
    public boolean shipsAreAllSunk();
}

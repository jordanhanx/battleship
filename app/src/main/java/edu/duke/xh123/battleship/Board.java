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
     * Get the information the view needs at the specific coordinate.
     * 
     * @param where is the Coordinate to find information.
     * @return the information the view needs, or null if nothing was at the
     *         coordinate.
     */
    public T whatIsAt(Coordinate where);
}
